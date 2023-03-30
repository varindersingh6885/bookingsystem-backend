package com.nagarro.nagp.dbhotels.model;

import java.util.HashSet;

public class HotelRoom {
	private int roomNumber;
	private float price;
	private HashSet<String> datesOccupied;

	public HotelRoom() {
		this.datesOccupied = new HashSet<>();
	}

	public HotelRoom(int roomNumber, float price, HashSet<String> datesOccupied) {
		super();
		this.roomNumber = roomNumber;
		this.price = price;
		this.datesOccupied = datesOccupied;
	}

	public HotelRoom(int roomNumber, float price) {
		super();
		this.roomNumber = roomNumber;
		this.price = price;
		this.datesOccupied = new HashSet<>();
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public HashSet<String> getDatesOccupied() {
		return datesOccupied;
	}

	public void setDatesOccupied(HashSet<String> datesOccupied) {
		this.datesOccupied = datesOccupied;
	}
	
}
