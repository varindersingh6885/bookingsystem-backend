package com.nagarro.nagp.dbflights.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.dbflights.constants.FlightSeatStatus;
import com.nagarro.nagp.dbflights.dto.FlightSearchParameters;
import com.nagarro.nagp.dbflights.dto.OrderFlight;
import com.nagarro.nagp.dbflights.dto.OrderStatus;
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
			return allFlights;
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

	@Override
	public synchronized  OrderFlight updateSeatsStatus(OrderFlight booking) {
		// first check if all seats are available
		String flightId = booking.getFlightId();
		List<Integer> seatsToBook = booking.getSeatNumbers();
		
		Flight f = flightRepo.getFlightByFlightID(flightId);
		boolean isAvailable = true;
		for(Integer seatNumber : seatsToBook) {
			boolean isVacant = f.getSeats().get(seatNumber-1).getStatus().equals(FlightSeatStatus.AVAILABLE);
			if(isVacant == false) {
				isAvailable = false;
				break;
			}
		}
		if(isAvailable) {
			for(Integer seatNumber : seatsToBook) {
				f.getSeats().get(seatNumber-1).setStatus(FlightSeatStatus.BOOKED);
			}
			booking.setOrderStatus(OrderStatus.CONFIRMED);
			booking.setRemarks("Your Seats "+booking.getSeatNumbers()+ " have been booked successfully. Enjoy safe journey.");
		} else {
			booking.setOrderStatus(OrderStatus.UNCONFIRMED);
			booking.setRemarks("Booking Failed! Seats not available");
		}
		return booking;
	}

}
