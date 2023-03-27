package com.nagarro.nagp.dbflights.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nagarro.nagp.dbflights.dto.FlightSearchParameters;
import com.nagarro.nagp.dbflights.model.Flight;
import com.nagarro.nagp.dbflights.repository.FlightRepository;
import com.nagarro.nagp.dbflights.service.FlightService;

public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepo;
	
	@Override
	public List<Flight> getAllFlights(FlightSearchParameters fsp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flight getFlightByFlightId(String flightId) {
		// TODO Auto-generated method stub
		return flightRepo.getFlightByFlightID(flightId);
	}

}
