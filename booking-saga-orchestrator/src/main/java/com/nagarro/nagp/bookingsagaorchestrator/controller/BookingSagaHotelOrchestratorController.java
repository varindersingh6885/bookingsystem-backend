package com.nagarro.nagp.bookingsagaorchestrator.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.nagarro.nagp.bookingsagaorchestrator.model.OrderHotel;
import com.nagarro.nagp.bookingsagaorchestrator.util.JsonSerializerUtil;

@Controller
public class BookingSagaHotelOrchestratorController {
	
	Logger logger = LogManager.getLogger(BookingSagaHotelOrchestratorController.class);
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "OrderHotelRequestReceivedEvent")
	public void newHotelOrderRequestReceived(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelRequestReceivedEvent");
		jmsTemplate.convertAndSend("OrderHotelCheckRoomsAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderHotelCheckRoomsAvailableFail")
	public void orderHotelCheckRoomsAvailableFail(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);

		logger.info("ActiveMqEvent OrderHotelCheckRoomsAvailableFail");
		jmsTemplate.convertAndSend("UpdateBookingHotelRoomsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "RoomsAvailableInitiatePaymentRequest")
	public void seatsAvailableInitiatePaymentRequest(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);

		logger.info("ActiveMqEvent RoomsAvailableInitiatePaymentRequest");
		jmsTemplate.convertAndSend("UpdateBookingHotelPaymentPending",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderHotelPaymentReceived")
	public void orderHotelPaymentReceived(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelPaymentReceived");
		jmsTemplate.convertAndSend("OrderHotelPaymentReceivedUpdate",JsonSerializerUtil.serialize(booking));
	}

	@JmsListener(destination = "OrderHotelPaymentFailed")
	public void orderHotelPaymentFailed(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelPaymentFailed");
		jmsTemplate.convertAndSend("OrderHotelPaymentFailedUpdate",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderHotelBookRooms")
	public void orderHotelBookRooms(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelBookRooms");
		jmsTemplate.convertAndSend("OrderHotelBookRoomsConfirm",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderHotelBookRoomsConfirmSuccess")
	public void orderHotelBookRoomsConfirmSuccess(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelBookRoomsConfirmSuccess");
		jmsTemplate.convertAndSend("UpdateBookingHotelBookingConfirmed",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderHotelBookRoomsConfirmFail")
	public void orderHotelBookRoomsConfirmFail(String orderPayloadHotel) {
		OrderHotel booking = JsonSerializerUtil.orderPayloadHotel(orderPayloadHotel);
		
		logger.info("ActiveMqEvent OrderHotelBookRoomsConfirmFail");
		jmsTemplate.convertAndSend("UpdateBookingHotelRoomsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
}
