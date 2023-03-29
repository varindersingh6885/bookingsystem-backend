package com.nagarro.nagp.bookingservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.bookingservice.dto.NewFlightOrder;
import com.nagarro.nagp.bookingservice.model.OrderFlight;
import com.nagarro.nagp.bookingservice.model.OrderStatus;
import com.nagarro.nagp.bookingservice.repository.OrderFlightRepository;
import com.nagarro.nagp.bookingservice.service.OrderFlightService;
import com.nagarro.nagp.bookingservice.util.JsonSerializerUtil;

@Service
public class OrderFlightServiceImpl implements OrderFlightService {

	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private OrderFlightRepository orderFlightRepo;
	
	@Override
	public OrderFlight createFlightOrder(NewFlightOrder order) {
		
		OrderFlight booking = new OrderFlight();
		booking.setBookingId(UUID.randomUUID().toString());
		booking.setOrderStatus(OrderStatus.PROCESSING);
		booking.setAmount(calAmount(order.getSeatNumbers()));
		booking.setUsername(order.getUsername());
		booking.setFlightId(order.getFlightId());
		booking.setSeatNumbers(order.getSeatNumbers());
		booking.setRemarks("Booking is being initiated. Use the bookingId to track booking status");
		
		orderFlightRepo.addFlightOrder(booking);
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderFlightRequestReceivedEvent",JsonSerializerUtil.serialize(booking));
		
		return booking;
	}

	@Override
	public OrderFlight getFlightOrder(String bookingId) {
		OrderFlight booking = orderFlightRepo.getFlightOrder(bookingId);
		return booking;
	}
	
	private float calAmount(List<Integer> seatNumbers) {
		float totalAmount = 0;
		for(Integer seatNum : seatNumbers) {
			if(seatNum <= 20) {
				totalAmount += 1000;
			} else {
				totalAmount += 2500;
			}
		}
		return totalAmount;
	}
	
	@JmsListener(destination = "UpdateBookingFlightSeatsNotAvailable")
	public void updateBookingFlightSeatsNotAvailable(String orderPayload) {
		OrderFlight bookingUpdate = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderFlight booking = orderFlightRepo.getFlightOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderFlightRepo.updateFlightOrder(booking);
	}
	
	@JmsListener(destination = "UpdateBookingFlightBookingConfirmed")
	public void updateBookingFlightBookingConfirmed(String orderPayload) {
		OrderFlight bookingUpdate = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderFlight booking = orderFlightRepo.getFlightOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderFlightRepo.updateFlightOrder(booking);
	}
	
	@JmsListener(destination = "UpdateBookingFlightPaymentPending")
	public void seatsAvailableInitiatePaymentRequest(String orderPayload) {
		OrderFlight bookingUpdate = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderFlight booking = orderFlightRepo.getFlightOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderFlightRepo.updateFlightOrder(booking);
	}
	
	@JmsListener(destination = "OrderFlightPaymentReceivedUpdate")
	public void orderFlightBookSeats(String orderPayload) {
		OrderFlight bookingUpdate = JsonSerializerUtil.orderPayload(orderPayload);
		
		OrderFlight booking = orderFlightRepo.getFlightOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderFlightRepo.updateFlightOrder(booking);
		
		System.out.println("book seats now");
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderFlightBookSeats",JsonSerializerUtil.serialize(booking));
	}
}
