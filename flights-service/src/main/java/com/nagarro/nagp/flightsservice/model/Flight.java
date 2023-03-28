package com.nagarro.nagp.flightsservice.model;

import java.util.List;

public class Flight {
	private String flightId;
	private String companyName;
	private String source;
	private String destination;
	private String departureDate;
	private String departureTime;
	List<FlightSeat> seats;
	
	public Flight() {
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
	
}
