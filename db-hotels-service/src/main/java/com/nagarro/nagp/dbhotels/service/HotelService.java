package com.nagarro.nagp.dbhotels.service;

import java.util.List;

import com.nagarro.nagp.dbhotels.dto.HotelSearchParameters;
import com.nagarro.nagp.dbhotels.dto.OrderHotel;
import com.nagarro.nagp.dbhotels.model.Hotel;

public interface HotelService {
	public List<Hotel> getAllHotels(HotelSearchParameters hsp);
	public Hotel getHotelByHotelId(String hotelId);
	public OrderHotel updateRoomStatus(OrderHotel booking);
}
