package com.nagarro.nagp.bookingservice.service.impl;

import java.util.List;
import java.util.UUID;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.bookingservice.dto.NewHotelOrder;
import com.nagarro.nagp.bookingservice.model.OrderFlight;
import com.nagarro.nagp.bookingservice.model.OrderHotel;
import com.nagarro.nagp.bookingservice.model.OrderStatus;
import com.nagarro.nagp.bookingservice.repository.OrderFlightRepository;
import com.nagarro.nagp.bookingservice.repository.OrderHotelRepository;
import com.nagarro.nagp.bookingservice.service.OrderHotelService;
import com.nagarro.nagp.bookingservice.util.JsonSerializerUtil;
import com.nagarro.nagp.bookingservice.util.PriceUtil;

public class OrderHotelServiceImpl implements OrderHotelService {
	
	Logger logger = LogManager.getLogger(OrderFlightServiceImpl.class);
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private OrderHotelRepository orderHotelRepo;

	@Override
	public OrderHotel createHotelOrder(NewHotelOrder order) {
		OrderHotel booking = new OrderHotel();
		booking.setBookingId(UUID.randomUUID().toString());
		booking.setOrderStatus(OrderStatus.PROCESSING);
		booking.setAmount(PriceUtil.calAmount(order.getRoomsRequired()));
		booking.setUsername(order.getUsername());
		booking.setHotelId(order.getHotelId());
		booking.setRoomsRequired(order.getRoomsRequired());
		booking.setRemarks("Booking is being initiated. Use the bookingId to track booking status");
		booking.setCheckInDate(order.getCheckInDate());
		booking.setCheckOutDate(order.getCheckOutDate());
		orderHotelRepo.addHotelOrder(booking);
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderHotelRequestReceivedEvent",JsonSerializerUtil.serialize(booking));
		
		return booking;
	}

	@Override
	public OrderHotel getHotelOrder(String bookingId) {
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingId);
		return booking;
	}
	
	@JmsListener(destination = "UpdateBookingHotelRoomsNotAvailable")
	public void updateBookingHotelRoomsNotAvailable(String orderPayload) {
		OrderHotel bookingUpdate = JsonSerializerUtil.orderPayloadHotel(orderPayload);
		
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		logger.info("ActiveMqEvent UpdateBookingHotelRoomsNotAvailable");
		
		orderHotelRepo.updateHotelOrder(booking);
	}
	
	@JmsListener(destination = "UpdateBookingHotelBookingConfirmed")
	public void updateBookingHotelBookingConfirmed(String orderPayload) {
		
		logger.info("ActiveMqEvent UpdateBookingHotelBookingConfirmed");

		OrderHotel bookingUpdate = JsonSerializerUtil.orderPayloadHotel(orderPayload);
		
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		booking.setRoomsNumbersAllocated(bookingUpdate.getRoomsNumbersAllocated());
		
		
		orderHotelRepo.updateHotelOrder(booking);
	}
	
	@JmsListener(destination = "UpdateBookingHotelPaymentPending")
	public void updateBookingHotelPaymentPending(String orderPayload) {
		
		logger.info("ActiveMqEvent UpdateBookingHotelPaymentPending");
		
		OrderHotel bookingUpdate = JsonSerializerUtil.orderPayloadHotel(orderPayload);
		
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderHotelRepo.updateHotelOrder(booking);
	}
	
	@JmsListener(destination = "OrderHotelPaymentReceivedUpdate")
	public void orderHotelPaymentReceivedUpdate(String orderPayload) {
		
		logger.info("ActiveMqEvent OrderHotelPaymentReceivedUpdate");
		
		OrderHotel bookingUpdate = JsonSerializerUtil.orderPayloadHotel(orderPayload);
		
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderHotelRepo.updateHotelOrder(booking);
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderHotelBookRooms",JsonSerializerUtil.serialize(booking));
	}

	@JmsListener(destination = "OrderHotelPaymentFailedUpdate")
	public void orderHotelPaymentFailedUpdate(String orderPayload) {
		
		logger.info("ActiveMqEvent OrderHotelPaymentFailedUpdate");
		
		OrderHotel bookingUpdate = JsonSerializerUtil.orderPayloadHotel(orderPayload);
		
		OrderHotel booking = orderHotelRepo.getHotelOrder(bookingUpdate.getBookingId());
		
		booking.setOrderStatus(bookingUpdate.getOrderStatus());
		booking.setRemarks(bookingUpdate.getRemarks());
		
		orderHotelRepo.updateHotelOrder(booking);
	}

}
