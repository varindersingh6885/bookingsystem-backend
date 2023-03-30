package com.nagarro.nagp.dbhotels.model;

import java.util.ArrayList;
import java.util.List;

import com.nagarro.nagp.dbhotels.constants.HotelRoomPrice;
import com.nagarro.nagp.dbhotels.constants.HotelRoomStatus;
import com.nagarro.nagp.dbhotels.constants.HotelRoomType;


public class Hotel {
	private String hotelId;
	private String hotelName;
	private String city;
	private String address;
	List<HotelRoom> rooms;
	
	public Hotel() {
		this.rooms = new ArrayList<>();
		addHotelRooms();
	}
	
	public Hotel(String hotelId, String hotelName, String city, String address, List<HotelRoom> rooms) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.city = city;
		this.address = address;
		this.rooms = rooms;
		
		addHotelRooms();
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

	private void addHotelRooms() {
		
		for(int i = 1; i <= 30; i++) {
			HotelRoom r = new HotelRoom(i, HotelRoomPrice.LUXURY_PRICE);
			rooms.add(r);
		}
		
	}
	
}
