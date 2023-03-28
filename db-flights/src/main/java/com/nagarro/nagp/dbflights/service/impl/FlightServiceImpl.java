package com.nagarro.nagp.dbflights.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.dbflights.dto.FlightSearchParameters;
import com.nagarro.nagp.dbflights.model.Flight;
import com.nagarro.nagp.dbflights.repository.FlightRepository;
import com.nagarro.nagp.dbflights.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepo;
	
	@Override
	public List<Flight> getAllFlights(FlightSearchParameters fsp) {
		List<Flight> allFlights = flightRepo.getAllFlights();
		
		if(fsp.getSource() == null && 
				fsp.getDestination() == null && 
				fsp.getDepartureDate() == null
		) {
			return new ArrayList<>();
		}
		List<Flight> currentFlights = allFlights;
		List<Flight> filteredFlights = new ArrayList<>();
		
		// filter by source
		if(fsp.getSource() != null) {
			for(Flight f : currentFlights) {
				if(f.getSource().toLowerCase().equals(fsp.getSource().toLowerCase())) {
					filteredFlights.add(f);
				}
			}
			currentFlights = filteredFlights;
		} 
		
		
		// filter by destination
		if(fsp.getDestination() != null) {
			filteredFlights = new ArrayList<>();
			for(Flight f : currentFlights) {
				if(f.getDestination().toLowerCase().equals(fsp.getDestination().toLowerCase())) {
					filteredFlights.add(f);
				}
			}
			currentFlights = filteredFlights;
		}
		
		
		// filter by date
		if(fsp.getDepartureDate() != null) {
			filteredFlights = new ArrayList<>();
			for(Flight f : currentFlights) {
				if(f.getDepartureDate().toLowerCase().equals(fsp.getDepartureDate().toLowerCase())) {
					filteredFlights.add(f);
				}
			}
		}
		
		return filteredFlights;
	}

	@Override
	public Flight getFlightByFlightId(String flightId) {
		// TODO Auto-generated method stub
		return flightRepo.getFlightByFlightID(flightId);
	}

}
