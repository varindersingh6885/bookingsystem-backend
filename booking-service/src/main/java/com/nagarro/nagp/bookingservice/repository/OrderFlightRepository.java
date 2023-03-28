package com.nagarro.nagp.bookingservice.repository;

import com.nagarro.nagp.bookingservice.model.OrderFlight;

public interface OrderFlightRepository {
	OrderFlight addFlightOrder(OrderFlight order);
	OrderFlight getFlightOrder(String bookingId);
}
