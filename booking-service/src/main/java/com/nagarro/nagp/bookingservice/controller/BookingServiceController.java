package com.nagarro.nagp.bookingservice.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.bookingservice.dto.NewFlightOrder;
import com.nagarro.nagp.bookingservice.model.OrderFlight;
import com.nagarro.nagp.bookingservice.service.OrderFlightService;

@RestController
@RequestMapping("/booking/")
public class BookingServiceController {
	
	Logger logger = LogManager.getLogger(BookingServiceController.class);
	
	@Autowired
	private OrderFlightService orderFlightService;
	
	@PostMapping("/flights")
	public OrderFlight createFlightBooking(@RequestBody NewFlightOrder order) {
		
		logger.info("/booking/flights creating a new booking");
		OrderFlight orderGenerated = orderFlightService.createFlightOrder(order);
		return orderGenerated;
	}
	
	@GetMapping("flights/{id}")
	public OrderFlight getOrderFlightBooking(@PathVariable("id") String bookingId) {
		
		logger.info("/booking/flights get booking record");
		
		OrderFlight booking = orderFlightService.getFlightOrder(bookingId);
		return booking;
	}
}
