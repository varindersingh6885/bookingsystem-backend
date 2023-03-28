package com.nagarro.nagp.flightsservice.dto;

public class FlightSearchParameters {
	private String source;
	private String destination;
	private String departureDate;
	
	public FlightSearchParameters() {}
	public FlightSearchParameters(String source, String destination, String departureDate) {
		super();
		this.source = source;
		this.destination = destination;
		this.departureDate = departureDate;
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
	
	
}
