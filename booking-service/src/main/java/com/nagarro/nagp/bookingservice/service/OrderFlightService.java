package com.nagarro.nagp.bookingservice.service;

import com.nagarro.nagp.bookingservice.dto.NewFlightOrder;
import com.nagarro.nagp.bookingservice.model.OrderFlight;

public interface OrderFlightService {
	OrderFlight createFlightOrder(NewFlightOrder order);
	OrderFlight getFlightOrder(String bookingId);
}
