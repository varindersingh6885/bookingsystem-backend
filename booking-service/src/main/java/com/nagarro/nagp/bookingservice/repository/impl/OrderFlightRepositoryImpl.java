package com.nagarro.nagp.bookingservice.repository.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.bookingservice.model.OrderFlight;
import com.nagarro.nagp.bookingservice.repository.OrderFlightRepository;

@Repository
public class OrderFlightRepositoryImpl implements OrderFlightRepository {
	
	HashMap<String, OrderFlight> flightOrders;
	
	public OrderFlightRepositoryImpl() {
		flightOrders = new HashMap<>();
	}

	@Override
	public OrderFlight addFlightOrder(OrderFlight order) {
		flightOrders.put(order.getBookingId(), order);
		return order;
	}

	@Override
	public OrderFlight getFlightOrder(String bookingId) {
		if(flightOrders.containsKey(bookingId)) {
			return flightOrders.get(bookingId);
		}
		return null;
	}

}
