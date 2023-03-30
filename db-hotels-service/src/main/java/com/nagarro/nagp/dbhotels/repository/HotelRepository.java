package com.nagarro.nagp.dbhotels.repository;

import java.util.List;

import com.nagarro.nagp.dbhotels.model.Hotel;

public interface HotelRepository {
	public List<Hotel> getAllHotels();
	public Hotel getHotelByHotelID(String hotelId);
	public Hotel updateRoomStatus(String hotelId, int roomNumber, String dateOccupied);
}
