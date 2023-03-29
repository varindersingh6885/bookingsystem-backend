package com.nagarro.nagp.flightsservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.flightsservice.constants.FlightSeatStatus;
import com.nagarro.nagp.flightsservice.dto.FlightSearchParameters;
import com.nagarro.nagp.flightsservice.dto.FlightWithoutSeatsData;
import com.nagarro.nagp.flightsservice.model.Flight;
import com.nagarro.nagp.flightsservice.model.FlightSeat;
import com.nagarro.nagp.flightsservice.model.OrderFlight;
import com.nagarro.nagp.flightsservice.model.OrderStatus;
import com.nagarro.nagp.flightsservice.service.FlightService;
import com.nagarro.nagp.flightsservice.util.JsonSerializerUtil;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
	
	@Override
	public List<FlightWithoutSeatsData> getAllFlights(FlightSearchParameters fsp) {
		// TODO Auto-generated method stub
		RestTemplate restClient = new RestTemplate();
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Flight[] flights = circuitBreaker.run(() -> {
            System.out.println("Attempt");
            List<ServiceInstance> dbFlightsService = discoveryClient.getInstances("DB-FLIGHTS");
            String dbFlightsUri = dbFlightsService.get(0).getUri().toString();
            
            dbFlightsUri += "/flights?";
            if(fsp.getSource() != null) dbFlightsUri += "source=" + fsp.getSource() + "&";
            if(fsp.getDestination() != null) dbFlightsUri += "destination=" + fsp.getDestination() + "&";
            if(fsp.getDepartureDate() != null) dbFlightsUri += "departureDate=" + fsp.getDepartureDate() + "&";
        	
            return restClient.getForObject(dbFlightsUri, Flight[].class);
        }, throwable -> {
//        	System.out.println("db-flights service down");
            return null;
        });
        if(flights == null) {
        	throw new RuntimeException("Service is down currently. Try again Later");
        }
        List<FlightWithoutSeatsData> availableFlights = new ArrayList<>();
        for(Flight f : flights) {
        	availableFlights.add(new FlightWithoutSeatsData(f));
        }
		return availableFlights;
	}

	@Override
	public Flight getFlightByFlightId(String flightId) {
		RestTemplate restClient = new RestTemplate();
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Flight flight = circuitBreaker.run(() -> {
            System.out.println("Attempt");
            List<ServiceInstance> dbFlightsService = discoveryClient.getInstances("DB-FLIGHTS");
            String dbFlightsUri = dbFlightsService.get(0).getUri().toString();
            
            dbFlightsUri += "/flights/" + flightId;
            
        	
            return restClient.getForObject(dbFlightsUri, Flight.class);
        }, throwable -> {
//        	System.out.println("db-flights service down");
            return null;
        });
        if(flight == null) {
        	throw new RuntimeException("Service is down currently. Try again Later");
        }
        
		return flight;
	}
	
	@JmsListener(destination = "OrderFlightCheckSeatsAvailable")
	public void newFlightOrderRequestReceived(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		boolean areSeatsAvailable = checkSeatsAvailable(booking.getFlightId(), booking.getSeatNumbers(), booking);
		if(areSeatsAvailable) {
			booking.setOrderStatus(OrderStatus.PAYMENT_PENDING);
			booking.setRemarks("Seats are available proceed to payment using given uri " +
					"http://localhost:8001/payments/flights/"+booking.getBookingId() +
					"?amount="+booking.getAmount());
			jmsTemplate.convertAndSend("SeatsAvailableInitiatePaymentRequest",JsonSerializerUtil.serialize(booking));
		} else {
			jmsTemplate.convertAndSend("OrderFlightCheckSeatsAvailableFail",JsonSerializerUtil.serialize(booking));
		}
		// check seats available
	}
	
	
	@JmsListener(destination = "OrderFlightBookSeatsConfirm")
	public void orderFlightBookSeatsConfirm(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderFlight bookingUpdate = bookSeats(booking);
		if(bookingUpdate.getOrderStatus().equals(OrderStatus.CONFIRMED)) {

			jmsTemplate.convertAndSend("OrderFlightBookSeatsConfirmSuccess",JsonSerializerUtil.serialize(booking));
		} else {
			booking.setRemarks("Seats not available. Kindly be patient your amount will be refunded to your bank withing two working days.");
			booking.setOrderStatus(OrderStatus.UNCONFIRMED);
			jmsTemplate.convertAndSend("OrderFlightBookSeatsConfirmFail",JsonSerializerUtil.serialize(booking));
		}
		// check seats available
	}
	
	private boolean checkSeatsAvailable(String flightId, List<Integer> seatsRequired, OrderFlight booking) {
		
		RestTemplate restClient = new RestTemplate();
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Flight flight = circuitBreaker.run(() -> {
//            System.out.println("Attempt");
            List<ServiceInstance> dbFlightsService = discoveryClient.getInstances("DB-FLIGHTS");
            String dbFlightsUri = dbFlightsService.get(0).getUri().toString();
            
            dbFlightsUri += "/flights/" + flightId;
            
        	
            return restClient.getForObject(dbFlightsUri, Flight.class);
        }, throwable -> {
//        	System.out.println("db-flights service down");
        	booking.setRemarks("Internal Sever Error. Booking Failed");
        	booking.setOrderStatus(OrderStatus.UNCONFIRMED);
            return null;
        });
		
		if(flight == null) {
			return false;
		}
		
		// check if required seats are available
		// seats in db are from 0 -> 29
		for(Integer seatRequired : seatsRequired) {
			FlightSeat seat = flight.getSeats().get(seatRequired-1);
			if(!seat.getStatus().equals(FlightSeatStatus.AVAILABLE)) {
				booking.setOrderStatus(OrderStatus.UNCONFIRMED);
				booking.setRemarks("Seats not available");
				return false;
			}
		}
		return true;
	}
	
private OrderFlight bookSeats(OrderFlight booking) {
		
		RestTemplate restClient = new RestTemplate();
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        OrderFlight bookingUpdate = circuitBreaker.run(() -> {
//            System.out.println("Attempt");
            List<ServiceInstance> dbFlightsService = discoveryClient.getInstances("DB-FLIGHTS");
            String dbFlightsUri = dbFlightsService.get(0).getUri().toString();
            
            dbFlightsUri += "/flights/book";
            
        	
            return restClient.postForObject(dbFlightsUri, booking, OrderFlight.class);
        }, throwable -> {
//        	System.out.println("db-flights service down");
        	booking.setRemarks("Internal Sever Error. Booking Failed");
        	booking.setOrderStatus(OrderStatus.UNCONFIRMED);
            return null;
        });
		
		if(bookingUpdate == null) {
			return booking;
		}
		else {
        	booking.setRemarks(bookingUpdate.getRemarks());
        	booking.setOrderStatus(bookingUpdate.getOrderStatus());
		}
		
		return booking;
	}
}
