package com.nagarro.nagp.hotelsservice.dto;

public class HotelSearchParameters {
	private String hotelName;
	private String city;
	private String address;
	
	public HotelSearchParameters() {}

	public HotelSearchParameters(String hotelName, String city, String address) {
		super();
		this.hotelName = hotelName;
		this.city = city;
		this.address = address;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
