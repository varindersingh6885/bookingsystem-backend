package com.nagarro.nagp.dbflights.repository;

import java.util.List;

import com.nagarro.nagp.dbflights.model.Flight;

public interface FlightRepository {
	public List<Flight> getAllFlights();
	public Flight getFlightByFlightID(String flightId);
}
