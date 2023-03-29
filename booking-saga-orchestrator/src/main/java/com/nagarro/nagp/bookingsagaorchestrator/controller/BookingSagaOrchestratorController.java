package com.nagarro.nagp.bookingsagaorchestrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.nagarro.nagp.bookingsagaorchestrator.model.OrderFlight;
import com.nagarro.nagp.bookingsagaorchestrator.util.JsonSerializerUtil;

@Controller
public class BookingSagaOrchestratorController {
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = "OrderFlightRequestReceivedEvent")
	public void newFlightOrderRequestReceived(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);

		jmsTemplate.convertAndSend("OrderFlightCheckSeatsAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightCheckSeatsAvailableFail")
	public void orderFlightCheckSeatsAvailableFail(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);

		jmsTemplate.convertAndSend("UpdateBookingFlightSeatsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "SeatsAvailableInitiatePaymentRequest")
	public void seatsAvailableInitiatePaymentRequest(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);

		jmsTemplate.convertAndSend("UpdateBookingFlightPaymentPending",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightPaymentReceived")
	public void orderFlightPaymentReceived(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		jmsTemplate.convertAndSend("OrderFlightPaymentReceivedUpdate",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightBookSeats")
	public void orderFlightBookSeats(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		jmsTemplate.convertAndSend("OrderFlightBookSeatsConfirm",JsonSerializerUtil.serialize(booking));
	}
	
	@JmsListener(destination = "OrderFlightBookSeatsConfirmFail")
	public void orderFlightBookSeatsConfirmFail(String orderPayload) {
		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
		
		jmsTemplate.convertAndSend("UpdateBookingFlightSeatsNotAvailable",JsonSerializerUtil.serialize(booking));
	}
}
