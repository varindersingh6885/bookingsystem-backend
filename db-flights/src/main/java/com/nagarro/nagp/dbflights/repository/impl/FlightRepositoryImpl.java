package com.nagarro.nagp.dbflights.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.dbflights.constants.FlightSeatStatus;
import com.nagarro.nagp.dbflights.model.Flight;
import com.nagarro.nagp.dbflights.repository.FlightRepository;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
	List<Flight> flights;
	HashMap<String, Integer> flightsIndex;
	public FlightRepositoryImpl() {
		flights = new ArrayList<>();
		flightsIndex = new HashMap<>();
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
	
	@Override
	public Flight updateSeatStatus(String flightId, int seatNumber, String status) {
		Flight f = flights.get(flightsIndex.get(flightId));
		f.getSeats().get(seatNumber).setStatus(status);
		return f;
	}
	
	public void createDummyFlights() {
		Flight f1 = new Flight("AB1234", "Indigo", "Amritsar", "Delhi", "24-04-2023", "14:50", new ArrayList<>());
		Flight f2 = new Flight("DX9332", "Spicejet", "Mumbai", "Delhi", "24-04-2023", "16:50", new ArrayList<>());
		Flight f3 = new Flight("VF1936", "Vistara", "Delhi", "Amritsar", "26-04-2023", "10:40", new ArrayList<>());
		Flight f4 = new Flight("SM1731", "AirIndia", "Bangalore", "Mumbai", "24-04-2023", "17:30", new ArrayList<>());
		Flight f5 = new Flight("FC1239", "Indigo", "Amritsar", "Delhi", "24-04-2023", "22:10", new ArrayList<>());
		Flight f6 = new Flight("HR1834", "Emirates", "Chandigarh", "Chennai", "24-04-2023", "19:20", new ArrayList<>());
		
		flights.add(f1);
		flightsIndex.put(f1.getFlightId(), 0);
		flights.add(f2);
		flightsIndex.put(f2.getFlightId(), 1);
		flights.add(f3);
		flightsIndex.put(f3.getFlightId(), 2);
		flights.add(f4);
		flightsIndex.put(f4.getFlightId(), 3);
		flights.add(f5);
		flightsIndex.put(f5.getFlightId(), 4);
		flights.add(f6);
		flightsIndex.put(f6.getFlightId(), 5);
	}

}
