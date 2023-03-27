package com.nagarro.nagp.dbflights.dto;

public class FlightSearchParameters {
	private String source;
	private String destination;
	private String date;
	
	public FlightSearchParameters() {}
	public FlightSearchParameters(String source, String destination, String date) {
		super();
		this.source = source;
		this.destination = destination;
		this.date = date;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
