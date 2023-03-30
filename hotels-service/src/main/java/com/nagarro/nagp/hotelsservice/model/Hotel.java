package com.nagarro.nagp.hotelsservice.model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
	private String hotelId;
	private String hotelName;
	private String city;
	private String address;
	List<HotelRoom> rooms;
	
	public Hotel() {
		this.rooms = new ArrayList<>();
	}
	
	public Hotel(String hotelId, String hotelName, String city, String address, List<HotelRoom> rooms) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.city = city;
		this.address = address;
		this.rooms = rooms;
		
	}
	
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public List<HotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<HotelRoom> rooms) {
		this.rooms = rooms;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
