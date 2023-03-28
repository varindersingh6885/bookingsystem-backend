package com.nagarro.nagp.flightsservice.model;

public class FlightSeat {
	private int seatNumber;
	private String status;
	private String type;
	private float price;
	
	public FlightSeat() {}
	public FlightSeat(int seatNumber, String status, String type, float price) {
		super();
		this.seatNumber = seatNumber;
		this.status = status;
		this.type = type;
		this.price = price;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "FlightSeat [seatNumber=" + seatNumber + ", status=" + status + ", type=" + type + ", price=" + price
				+ "]";
	}
	
}
