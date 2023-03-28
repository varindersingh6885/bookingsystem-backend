package com.nagarro.nagp.flightsservice.dto;

import java.util.List;

public class OrderFlight {
	private String username;
	private String orderID;
	private List<Integer> seatNumbers;
	private String amount;
	private String flightId;
	private OrderStatus orderStatus;
	
	

	public OrderFlight(){ }

	public OrderFlight(String username, String orderID, List<Integer> seatNumbers, String amount, String flightId,
			OrderStatus orderStatus) {
		super();
		this.username = username;
		this.orderID = orderID;
		this.seatNumbers = seatNumbers;
		this.amount = amount;
		this.flightId = flightId;
		this.orderStatus = orderStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public List<Integer> getSeatNumbers() {
		return seatNumbers;
	}

	public void setSeatNumbers(List<Integer> seatNumbers) {
		this.seatNumbers = seatNumbers;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
