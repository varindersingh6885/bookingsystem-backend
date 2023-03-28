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
			booking.setOrderStatus(OrderStatus.PROCESSING);
			booking.setRemarks("Seats are available proceed to payment");
			jmsTemplate.convertAndSend("NextEvent",JsonSerializerUtil.serialize(booking));
		} else {
			jmsTemplate.convertAndSend("BookingFailedSeatsNotAva",JsonSerializerUtil.serialize(booking));
		}
		// check seats available
	}
	
	
	private boolean checkSeatsAvailable(String flightId, List<Integer> seatsRequired, OrderFlight booking) {
		
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
        	booking.setRemarks("Internal Sever Error. Booking Failed");
        	booking.setOrderStatus(OrderStatus.UNCONFIRMED);
            return null;
        });
		
		if(flight == null) {
			return false;
		}
		
		// check if required seats are available
		for(Integer seatRequired : seatsRequired) {
			FlightSeat seat = flight.getSeats().get(seatRequired);
			if(!seat.getStatus().equals(FlightSeatStatus.AVAILABLE)) {
				booking.setOrderStatus(OrderStatus.UNCONFIRMED);
				booking.setRemarks("Seats not available");
				return false;
			}
		}
		return true;
	}
}
