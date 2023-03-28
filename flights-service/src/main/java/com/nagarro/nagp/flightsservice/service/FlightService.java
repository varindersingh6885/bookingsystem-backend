package com.nagarro.nagp.flightsservice.service;

import java.util.List;

import com.nagarro.nagp.flightsservice.dto.FlightSearchParameters;
import com.nagarro.nagp.flightsservice.model.Flight;

public interface FlightService {
	public List<Flight> getAllFlights(FlightSearchParameters fsp);
	public Flight getFlightByFlightId(String flightId);
}
