package com.nagarro.nagp.hotelsservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.hotelsservice.dto.HotelSearchParameters;
import com.nagarro.nagp.hotelsservice.dto.HotelWithRoomCountRoomPriceWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.dto.HotelWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.dto.OrderHotel;
import com.nagarro.nagp.hotelsservice.dto.OrderStatus;
import com.nagarro.nagp.hotelsservice.model.Hotel;
import com.nagarro.nagp.hotelsservice.model.HotelRoom;
import com.nagarro.nagp.hotelsservice.service.HotelService;
import com.nagarro.nagp.hotelsservice.service.util.CustomDateUtil;
import com.nagarro.nagp.hotelsservice.service.util.JsonSerializerUtil;

@Service
public class HotelServiceImpl implements HotelService {
	
Logger logger = LogManager.getLogger(HotelServiceImpl.class);
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

	@Override
	public List<HotelWithoutRoomsData> getAllHotels(HotelSearchParameters hsp) {
		RestTemplate restClient = new RestTemplate();
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Hotel[] hotels = circuitBreaker.run(() -> {
        	logger.info("/hotels-service/hotels  Attempting /db-hotels-service/hotels");
        	
            List<ServiceInstance> dbHotelssService = discoveryClient.getInstances("DB-HOTELS-SERVICE");
            String dbHotelsUri = dbHotelssService.get(0).getUri().toString();
            
            dbHotelsUri += "/hotels?";
            if(hsp.getHotelName() != null) dbHotelsUri += "hotelName=" + hsp.getHotelName() + "&";
            if(hsp.getCity() != null) dbHotelsUri += "city=" + hsp.getCity() + "&";
            if(hsp.getAddress() != null) dbHotelsUri += "address=" + hsp.getAddress() + "&";
        	
            return restClient.getForObject(dbHotelsUri, Hotel[].class);
        }, throwable -> {
//        	System.out.println("db-hotels service down");
        	logger.error("db-hotels-service is down currently.");
            return null;
        });
        if(hotels == null) {
        	throw new RuntimeException("Service is down currently. Try again Later");
        }
        List<HotelWithoutRoomsData> availableHotels = new ArrayList<>();
        for(Hotel h : hotels) {
        	availableHotels.add(new HotelWithoutRoomsData(h));
        }
		return availableHotels;
	}

	@Override
	public HotelWithRoomCountRoomPriceWithoutRoomsData getHotelByHotelId(String hotelId, HttpServletResponse res) {
		RestTemplate restClient = new RestTemplate();
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Hotel hotel = circuitBreaker.run(() -> {
//            System.out.println("Attempt");
        	logger.info("/hotels-service/hotels  Attempting /db-hotels-service/hotels");
            List<ServiceInstance> dbHotelsService = discoveryClient.getInstances("DB-HOTELS-SERVICE");
            String dbHotelsUri = dbHotelsService.get(0).getUri().toString();
            
            dbHotelsUri += "/hotels/" + hotelId;
            
        	
            return restClient.getForObject(dbHotelsUri, Hotel.class);
        }, throwable -> {
        	res.setStatus(500);
        	logger.error("db-hotels-service is down currently.");
        	throw new RuntimeException("Service is down currently. Try again Later");
//            return null;
        });
        if(hotel == null) {
        	res.setStatus(404);
        	return null;
        } else {
        	return new HotelWithRoomCountRoomPriceWithoutRoomsData(hotel);
        }
        
	}
	
	@JmsListener(destination = "OrderHotelCheckRoomsAvailable")
	public void orderHotelCheckRoomsAvailable(String orderPayload) {
		
		logger.info("ActiveMqEvent OrderHotelCheckRoomsAvailable");
		OrderHotel booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		boolean areRoomsAvailable = checkRoomsAvailable(booking.getHotelId(), booking.getRoomsRequired(), booking);
		if(areRoomsAvailable) {
			booking.setOrderStatus(OrderStatus.PAYMENT_PENDING);
			booking.setRemarks("Rooms are available proceed to payment using given uri " +
					"http://localhost:8001/payments/hotels/"+booking.getBookingId() +
					"?amount="+booking.getAmount());
			jmsTemplate.convertAndSend("RoomsAvailableInitiatePaymentRequest",JsonSerializerUtil.serialize(booking));
		} else {
			jmsTemplate.convertAndSend("OrderHotelCheckRoomsAvailableFail",JsonSerializerUtil.serialize(booking));
		}
		// check seats available
	}
	
	private boolean checkRoomsAvailable(String hotelId, int roomsRequired, OrderHotel booking) {
		
		RestTemplate restClient = new RestTemplate();
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Hotel hotel = circuitBreaker.run(() -> {
//            System.out.println("Attempt");
            List<ServiceInstance> dbHotelsService = discoveryClient.getInstances("DB-HOTELS-SERVICE");
            String dbFlightsUri = dbHotelsService.get(0).getUri().toString();
            
            dbFlightsUri += "/hotels/" + hotelId;
            
        	
            return restClient.getForObject(dbFlightsUri, Hotel.class);
        }, throwable -> {
//        	System.out.println("db-hotels service down");
        	logger.error("db-hotels-service is down currently.");
        	booking.setRemarks("Internal Sever Error. Booking Failed");
        	booking.setOrderStatus(OrderStatus.UNCONFIRMED);
            return null;
        });
		
		if(hotel == null) {
			return false;
		}
		
		// check if required rooms are available
		// rooms in db are from 0 -> 29
		
		List<String> datesRequired = CustomDateUtil.getIndividualDates(booking.getCheckInDate(), booking.getCheckOutDate());

		int roomsAvailableCount = 0;
		List<HotelRoom> roomsAvailableList = new ArrayList<>();
		for(HotelRoom room : hotel.getRooms()) {
			boolean isVacant = true;
			for(String dateRequired : datesRequired) {
				if(room.getDatesOccupied().contains(dateRequired)) {
					isVacant = false;
					break;
				}
			}
			if(isVacant) {
				roomsAvailableList.add(room);
				roomsAvailableCount++;
			}
		}
		if(roomsAvailableCount < roomsRequired) {
			return false;
		}
		return true;
	}
	
	@JmsListener(destination = "OrderHotelBookRoomsConfirm")
	public void orderHotelBookRoomsConfirm(String orderPayload) {
		
		logger.info("ActiveMqEvent OrderHotelBookRoomsConfirm");
		
		OrderHotel booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderHotel bookingUpdate = bookSeats(booking);
		if(bookingUpdate.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
			booking.setRemarks("Your hotel booking is confirmed. Enjoy happy stay.");

			jmsTemplate.convertAndSend("OrderHotelBookRoomsConfirmSuccess",JsonSerializerUtil.serialize(booking));
		} else {
			booking.setRemarks("Rooms not available. Kindly be patient your amount will be refunded to your bank withing two working days.");
			booking.setOrderStatus(OrderStatus.UNCONFIRMED);
			jmsTemplate.convertAndSend("OrderHotelBookRoomsConfirmFail",JsonSerializerUtil.serialize(booking));
		}
		// check seats available
	}
	
	private OrderHotel bookSeats(OrderHotel booking) {
		
		RestTemplate restClient = new RestTemplate();
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
		OrderHotel bookingUpdate = circuitBreaker.run(() -> {
//            System.out.println("Attempt");
            List<ServiceInstance> dbHotelService = discoveryClient.getInstances("DB-HOTELS-SERVICE");
            String dbHotelUri = dbHotelService.get(0).getUri().toString();
            
            dbHotelUri += "/hotels/book";
            
        	
            return restClient.postForObject(dbHotelUri, booking, OrderHotel.class);
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
