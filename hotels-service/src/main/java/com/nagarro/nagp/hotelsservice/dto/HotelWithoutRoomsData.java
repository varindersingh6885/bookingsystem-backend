package com.nagarro.nagp.hotelsservice.dto;

import com.nagarro.nagp.hotelsservice.model.Hotel;

public class HotelWithoutRoomsData {
	private String hotelId;
	private String hotelName;
	private String city;
	private String address;
	
	public HotelWithoutRoomsData() {
	}
	
	public HotelWithoutRoomsData(String hotelId, String hotelName, String city, String address) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.city = city;
		this.address = address;
	}
	
	public HotelWithoutRoomsData(Hotel h) {
		this.hotelId = h.getHotelId();
		this.hotelName = h.getHotelName();
		this.city = h.getCity();
		this.address = h.getAddress();
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
