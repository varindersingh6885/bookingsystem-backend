package com.nagarro.nagp.bookingservice.model;

import java.util.List;

public class OrderFlight {
	private String bookingId;
	private String username;
	private float amount;
	private String flightId;
	private List<Integer> seatNumbers;
	private OrderStatus orderStatus;
	private String remarks;
	
	public OrderFlight() {}
	
	public OrderFlight(String bookingId, String username, float amount, String flightId, List<Integer> seatNumbers,
			OrderStatus orderStatus, String remarks) {
		super();
		this.bookingId = bookingId;
		this.username = username;
		this.amount = amount;
		this.flightId = flightId;
		this.seatNumbers = seatNumbers;
		this.orderStatus = orderStatus;
		this.remarks = remarks;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
