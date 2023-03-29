package com.nagarro.nagp.bookingservice.controller;

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
	
	@Autowired
	private OrderFlightService orderFlightService;
	
	@PostMapping("/flight")
	public OrderFlight createAnFlightBooking(@RequestBody NewFlightOrder order) {
		OrderFlight orderGenerated = orderFlightService.createFlightOrder(order);
		return orderGenerated;
	}
	
	@GetMapping("/{id}")
	public OrderFlight getOrderFlightBooking(@PathVariable("id") String bookingId) {
		OrderFlight booking = orderFlightService.getFlightOrder(bookingId);
		return booking;
	}
}
