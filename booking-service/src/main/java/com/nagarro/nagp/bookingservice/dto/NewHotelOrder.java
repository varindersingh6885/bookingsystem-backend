package com.nagarro.nagp.bookingservice.dto;

public class NewHotelOrder {
	private String username;
	private String hotelId;
	private String checkInDate;
	private String checkOutDate;
	private int roomsRequired;
	
	public NewHotelOrder() {}
	public NewHotelOrder(String username, String hotelId, String checkInDate, String checkOutDate, int roomsRequired) {
		super();
		this.username = username;
		this.hotelId = hotelId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomsRequired = roomsRequired;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public int getRoomsRequired() {
		return roomsRequired;
	}
	public void setRoomsRequired(int roomsRequired) {
		this.roomsRequired = roomsRequired;
	}
	
}
