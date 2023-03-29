package com.nagarro.nagp.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.paymentservice.model.OrderFlight;
import com.nagarro.nagp.paymentservice.service.PaymentsService;


@RestController
@RequestMapping("/payments")
public class PaymentsServiceController {
	
	@Autowired
	private PaymentsService paymentService;
	
	@PostMapping("/flights/{bookingID}")
	public String acceptPaymentFlights(@PathVariable("bookingID") String bookingId, @RequestParam(required = false, name = "fail") String doFail) {
		
		if(doFail != null && doFail.toLowerCase().equals("true")) {
			paymentService.mockPaymentFailure(bookingId);
			return "payment not successful";
		} else {
			
			// fire event to mark seats as booked
			OrderFlight booking = new OrderFlight();
			booking.setBookingId(bookingId);
			
			String msg = paymentService.paymentReceived(bookingId);
			
			return msg;
			
		}
	}
	
//	@JmsListener(destination = "OrderFlightRequestReceivedEvent")
//	public void newFlightOrderRequestReceived(String orderPayload) {
//		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
//
//		jmsTemplate.convertAndSend("OrderFlightCheckSeatsAvailable",JsonSerializerUtil.serialize(booking));
//	}
//	
//	@JmsListener(destination = "SeatsAvailableInitiatePaymentRequest")
//	public void seatsAvailableInitiatePaymentRequest(String orderPayload) {
//		OrderFlight booking = JsonSerializerUtil.orderPayload(orderPayload);
//
//		jmsTemplate.convertAndSend("UpdateBookingFlightPaymentPending",JsonSerializerUtil.serialize(booking));
//	}
}
