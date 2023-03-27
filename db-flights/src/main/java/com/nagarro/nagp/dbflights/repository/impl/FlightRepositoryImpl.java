package com.nagarro.nagp.dbflights.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.dbflights.model.Flight;
import com.nagarro.nagp.dbflights.repository.FlightRepository;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
	List<Flight> flights;
	public FlightRepositoryImpl() {
		flights = new ArrayList<>();
		createDummyFlights();
	}

	@Override
	public List<Flight> getAllFlights() {
		return flights;
	}

	@Override
	public Flight getFlightByFlightID(String flightId) {
		for(int i = 0; i < flights.size(); i++) {
			if(flights.get(i).getFlightId().equals(flightId))
				return flights.get(i);
		}
		return null;
	}
	
	
	public void createDummyFlights() {
		flights.add( new Flight("AB1234", "Indigo", "Amritsar", "Delhi", "24-04-2023", "14:50", new ArrayList<>()));
		flights.add( new Flight("DX9332", "Spicejet", "Mumbai", "Delhi", "24-04-2023", "16:50", new ArrayList<>()));
		flights.add( new Flight("VF1936", "Vistara", "Delhi", "Amritsar", "26-04-2023", "10:40", new ArrayList<>()));
		flights.add( new Flight("SM1731", "AirIndia", "Bangalore", "Mumbai", "24-04-2023", "17:30", new ArrayList<>()));
		flights.add( new Flight("FC1239", "Indigo", "Amritsar", "Delhi", "24-04-2023", "22:10", new ArrayList<>()));
		flights.add( new Flight("HR1834", "Emirates", "Chandigarh", "Chennai", "24-04-2023", "19:20", new ArrayList<>()));
		
	}

}
