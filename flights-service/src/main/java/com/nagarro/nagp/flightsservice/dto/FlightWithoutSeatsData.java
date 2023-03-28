package com.nagarro.nagp.flightsservice.dto;

import com.nagarro.nagp.flightsservice.model.Flight;

public class FlightWithoutSeatsData {
	private String flightId;
	private String companyName;
	private String source;
	private String destination;
	private String departureDate;
	private String departureTime;
	
	
	public FlightWithoutSeatsData() {}
	public FlightWithoutSeatsData(String flightId, String companyName, String source, String destination,
			String departureDate, String departureTime) {
		super();
		this.flightId = flightId;
		this.companyName = companyName;
		this.source = source;
		this.destination = destination;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
	}
	public FlightWithoutSeatsData(Flight f) {
		this.flightId = f.getFlightId();
		this.companyName = f.getCompanyName();
		this.source = f.getSource();
		this.destination = f.getDestination();
		this.departureDate = f.getDepartureDate();
		this.departureTime = f.getDepartureTime();
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
	
}
