package com.nagarro.nagp.paymentservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.paymentservice.model.OrderFlight;
import com.nagarro.nagp.paymentservice.model.OrderHotel;
import com.nagarro.nagp.paymentservice.model.OrderStatus;
import com.nagarro.nagp.paymentservice.service.PaymentsService;
import com.nagarro.nagp.paymentservice.util.JsonSerializerUtil;

@Service
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@Override
	public String paymentReceivedFlight(String bookingId) {
		
		OrderFlight booking = new OrderFlight();
		booking.setBookingId(bookingId);
		booking.setOrderStatus(OrderStatus.PAYMENT_RECEIVED);
		booking.setRemarks("Payment is received. Please wait for the order confirmation");
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderFlightPaymentReceived",JsonSerializerUtil.serialize(booking));
		
		return "payment is successfull";
	}

	@Override
	public void mockPaymentFailureFlight(String bookingId) {
		OrderFlight booking = new OrderFlight();
		booking.setBookingId(bookingId);
		booking.setOrderStatus(OrderStatus.PAYMENT_FAILED);
		booking.setRemarks("Payment Failed! Kindly create a new booking");
		
		jmsTemplate.convertAndSend("OrderFlightPaymentFailed",JsonSerializerUtil.serialize(booking));
	}

	@Override
	public String paymentReceivedHotel(String bookingId) {
		OrderHotel booking = new OrderHotel();
		booking.setBookingId(bookingId);
		booking.setOrderStatus(OrderStatus.PAYMENT_RECEIVED);
		booking.setRemarks("Payment is received. Please wait for the order confirmation");
		
		//ActiveMq
		jmsTemplate.convertAndSend("OrderHotelPaymentReceived",JsonSerializerUtil.serialize(booking));
		
		return "payment is successfull";
	}

	@Override
	public void mockPaymentFailureHotel(String bookingId) {
		OrderHotel booking = new OrderHotel();
		booking.setBookingId(bookingId);
		booking.setOrderStatus(OrderStatus.PAYMENT_FAILED);
		booking.setRemarks("Payment Failed! Kindly create a new booking");
		
		jmsTemplate.convertAndSend("OrderHotelPaymentFailed",JsonSerializerUtil.serialize(booking));
		
	}

}
