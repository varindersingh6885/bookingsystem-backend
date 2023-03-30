package com.nagarro.nagp.paymentservice.model;

import java.util.ArrayList;
import java.util.List;

public class OrderHotel {
	private String bookingId;
	private String username;
	private float amount;
	private String hotelId;
	private String checkInDate;
	private String checkOutDate;
	private int roomsRequired;
	private OrderStatus orderStatus;
	private String remarks;
	private List<Integer> roomsNumbersAllocated;
	
	public OrderHotel() {
		this.roomsNumbersAllocated = new ArrayList<>();
	}

	public OrderHotel(String bookingId, String username, float amount, String hotelId, String checkInDate,
			String checkOutDate, int roomsRequired, OrderStatus orderStatus, String remarks,
			List<Integer> roomsNumbersAllocated) {
		super();
		this.bookingId = bookingId;
		this.username = username;
		this.amount = amount;
		this.hotelId = hotelId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.roomsRequired = roomsRequired;
		this.orderStatus = orderStatus;
		this.remarks = remarks;
		this.roomsNumbersAllocated = roomsNumbersAllocated;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public List<Integer> getRoomsNumbersAllocated() {
		return roomsNumbersAllocated;
	}

	public void setRoomsNumbersAllocated(List<Integer> roomsNumbersAllocated) {
		this.roomsNumbersAllocated = roomsNumbersAllocated;
	}

}
