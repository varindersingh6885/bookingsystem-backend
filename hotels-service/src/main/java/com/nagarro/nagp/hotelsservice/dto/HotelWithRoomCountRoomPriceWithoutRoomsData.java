package com.nagarro.nagp.hotelsservice.dto;

import com.nagarro.nagp.hotelsservice.model.Hotel;

public class HotelWithRoomCountRoomPriceWithoutRoomsData {
	private String hotelId;
	private String hotelName;
	private String city;
	private String address;
	private int totalRooms;
	private float singleRoomPrice;
	private String bedType;
	
	public HotelWithRoomCountRoomPriceWithoutRoomsData() {}
	public HotelWithRoomCountRoomPriceWithoutRoomsData(String hotelId, String hotelName, String city, String address,
			int totalRooms, int singleRoomPrice, String bedType) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.city = city;
		this.address = address;
		this.totalRooms = totalRooms;
		this.singleRoomPrice = singleRoomPrice;
		this.bedType = bedType;
	}
	public HotelWithRoomCountRoomPriceWithoutRoomsData(Hotel hotel) {
		super();
		this.hotelId = hotel.getHotelId();
		this.hotelName = hotel.getHotelName();
		this.city = hotel.getCity();
		this.address = hotel.getAddress();
		this.totalRooms = hotel.getRooms().size();
		this.singleRoomPrice = hotel.getRooms().get(0).getPrice(); // default static assumption
		this.bedType = "King Size"; // default static assumptions
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
	public int getTotalRooms() {
		return totalRooms;
	}
	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}
	public float getSingleRoomPrice() {
		return singleRoomPrice;
	}
	public void setSingleRoomPrice(int singleRoomPrice) {
		this.singleRoomPrice = singleRoomPrice;
	}
	
}
