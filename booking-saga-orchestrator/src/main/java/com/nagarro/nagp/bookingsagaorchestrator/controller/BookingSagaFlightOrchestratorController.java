package com.nagarro.nagp.bookingsagaorchestrator.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.nagarro.nagp.bookingsagaorchestrator.model.OrderFlight;
import com.nagarro.nagp.bookingsagaorchestrator.util.JsonSerializerUtil;

@Controller
public class BookingSagaFlightOrchestratorController {
	
	Logger logger = LogManager.getLogger(BookingSagaFlightOrchestratorController.class);
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "OrderFlightRequestReceivedEvent")
	public void newFlightOrderRequestReceived(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightRequestReceivedEvent");
		jmsTemplate.convertAndSend("OrderFlightCheckSeatsAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightCheckSeatsAvailableFail")
	public void orderFlightCheckSeatsAvailableFail(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);

		logger.info("ActiveMqEvent OrderFlightCheckSeatsAvailableFail");
		jmsTemplate.convertAndSend("UpdateBookingFlightSeatsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "SeatsAvailableInitiatePaymentRequest")
	public void seatsAvailableInitiatePaymentRequest(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);

		logger.info("ActiveMqEvent SeatsAvailableInitiatePaymentRequest");
		jmsTemplate.convertAndSend("UpdateBookingFlightPaymentPending",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightPaymentReceived")
	public void orderFlightPaymentReceived(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightPaymentReceived");
		jmsTemplate.convertAndSend("OrderFlightPaymentReceivedUpdate",JsonSerializerUtil.serialize(booking));
	}

	@JmsListener(destination = "OrderFlightPaymentFailed")
	public void orderFlightPaymentFailed(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightPaymentFailed");
		jmsTemplate.convertAndSend("OrderFlightPaymentFailedUpdate",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightBookSeats")
	public void orderFlightBookSeats(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightBookSeats");
		jmsTemplate.convertAndSend("OrderFlightBookSeatsConfirm",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightBookSeatsConfirmSuccess")
	public void orderFlightBookSeatsConfirmSuccess(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightBookSeatsConfirmSuccess");
		jmsTemplate.convertAndSend("UpdateBookingFlightBookingConfirmed",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightBookSeatsConfirmFail")
	public void orderFlightBookSeatsConfirmFail(String orderPayloadFlight) {
		OrderFlight booking = JsonSerializerUtil.orderPayloadFlight(orderPayloadFlight);
		
		logger.info("ActiveMqEvent OrderFlightBookSeatsConfirmFail");
		jmsTemplate.convertAndSend("UpdateBookingFlightSeatsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
}
