package com.nagarro.nagp.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.paymentservice.model.OrderFlight;
import com.nagarro.nagp.paymentservice.model.OrderHotel;
import com.nagarro.nagp.paymentservice.service.PaymentsService;


@RestController
@RequestMapping("/payments")
public class PaymentsServiceController {
	
	Logger logger = LogManager.getLogger(PaymentsServiceController.class);
	
	@Autowired
	private PaymentsService paymentService;
	
	@PostMapping("/flights/{bookingID}")
	public String acceptPaymentFlights(@PathVariable("bookingID") String bookingId, @RequestParam(required = false, name = "fail") String doFail) {
		logger.info("/payments processing flights payment");
		if(doFail != null && doFail.toLowerCase().equals("true")) {
			paymentService.mockPaymentFailureFlight(bookingId);
			logger.error("/payments payment failed");
			return "payment not successful";
		} else {
			
			// fire event to mark seats as booked
//			OrderFlight booking = new OrderFlight();
//			booking.setBookingId(bookingId);
			
			String msg = paymentService.paymentReceivedFlight(bookingId);
			logger.info("/payments payment received successfully");
			return msg;
			
		}
	}
	
	@PostMapping("/hotels/{bookingID}")
	public String acceptPaymentHotel(@PathVariable("bookingID") String bookingId, @RequestParam(required = false, name = "fail") String doFail) {
		logger.info("/payments processing hotels payment");
		if(doFail != null && doFail.toLowerCase().equals("true")) {
			paymentService.mockPaymentFailureHotel(bookingId);
			logger.error("/payments payment failed");
			return "payment not successful";
		} else {
			
			// fire event to mark seats as booked
//			OrderHotel booking = new OrderHotel();
//			booking.setBookingId(bookingId);
			
			String msg = paymentService.paymentReceivedHotel(bookingId);
			logger.info("/payments payment received successfully");
			return msg;
			
		}
	}
	
}
