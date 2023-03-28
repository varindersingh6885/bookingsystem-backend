package com.nagarro.nagp.bookingservice.model;

import java.util.List;

public class OrderFlight {
	private String bookingId;
	private String username;
	private String amount;
	private String flightId;
	private List<Integer> seatNumbers;
	private OrderStatus orderStatus;
	
	public OrderFlight() {}
	public OrderFlight(String username, String bookingId, String amount, String flightId, List<Integer> seatNumbers,
			OrderStatus orderStatus) {
		super();
		this.username = username;
		this.bookingId = bookingId;
		this.amount = amount;
		this.flightId = flightId;
		this.seatNumbers = seatNumbers;
		this.orderStatus = orderStatus;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
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
	
}
