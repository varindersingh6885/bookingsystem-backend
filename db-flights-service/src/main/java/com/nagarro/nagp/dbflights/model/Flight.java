package com.nagarro.nagp.dbflights.model;

import java.util.ArrayList;
import java.util.List;

import com.nagarro.nagp.dbflights.constants.FlightSeatPrice;
import com.nagarro.nagp.dbflights.constants.FlightSeatStatus;
import com.nagarro.nagp.dbflights.constants.FlightSeatType;

public class Flight {
	private String flightId;
	private String companyName;
	private String source;
	private String destination;
	private String departureDate;
	private String departureTime;
	List<FlightSeat> seats;
	
	public Flight() {
		this.seats = new ArrayList<>();
		addFlightSeats();
	}

	public Flight(String flightId, String companyName, String source, String destination, String departureDate,
			String departureTime, List<FlightSeat> seats) {
		super();
		this.flightId = flightId;
		this.companyName = companyName;
		this.source = source;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.seats = seats;
		
		addFlightSeats();
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public List<FlightSeat> getSeats() {
		return seats;
	}

	public void setSeats(List<FlightSeat> seats) {
		this.seats = seats;
	}
	
	private void addFlightSeats() {
		
		// add economy class seats 1 to 20
		for(int i = 1; i <= 20; i++) {
			FlightSeat s = new FlightSeat(i, FlightSeatStatus.AVAILABLE, FlightSeatType.ECONOMY, FlightSeatPrice.ECONOMY_PRICE);
			seats.add(s);
		}
		
		// add business class seats 21 to 30
		for(int i = 21; i <= 30; i++) {
			FlightSeat s = new FlightSeat(i, FlightSeatStatus.AVAILABLE, FlightSeatType.BUSINESS, FlightSeatPrice.BUSINESS_PRICE);
			seats.add(s);
		}
	}
	
}
