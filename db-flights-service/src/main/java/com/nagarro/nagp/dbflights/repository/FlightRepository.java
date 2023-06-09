package com.nagarro.nagp.dbflights.repository;

import java.util.List;

import com.nagarro.nagp.dbflights.constants.FlightSeatStatus;
import com.nagarro.nagp.dbflights.model.Flight;

public interface FlightRepository {
	public List<Flight> getAllFlights();
	public Flight getFlightByFlightID(String flightId);
	public Flight updateSeatStatus(String flightId, int seatNumber, String status);
}
