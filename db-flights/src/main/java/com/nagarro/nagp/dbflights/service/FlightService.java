package com.nagarro.nagp.dbflights.service;

import java.util.List;

import com.nagarro.nagp.dbflights.dto.FlightSearchParameters;
import com.nagarro.nagp.dbflights.model.Flight;

public interface FlightService {
	public List<Flight> getAllFlights(FlightSearchParameters fsp);
	public Flight getFlightByFlightId(String flightId);
}
