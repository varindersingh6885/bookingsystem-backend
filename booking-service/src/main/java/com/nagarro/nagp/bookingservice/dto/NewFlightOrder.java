package com.nagarro.nagp.bookingservice.dto;

import java.util.List;

import com.nagarro.nagp.bookingservice.model.OrderStatus;

public class NewFlightOrder {
	private String username;
	private String flightId;
	private List<Integer> seatNumbers;
	public NewFlightOrder() {}
	public NewFlightOrder(String username, String flightId, List<Integer> seatNumbers) {
		super();
		this.username = username;
		this.flightId = flightId;
		this.seatNumbers = seatNumbers;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlightId() {
		return flightId;
	}
	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}
	public List<Integer> getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(List<Integer> seatNumbers) {
		this.seatNumbers = seatNumbers;
	}
	
}
