package com.nagarro.nagp.hotelsservice.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.nagarro.nagp.hotelsservice.dto.HotelSearchParameters;
import com.nagarro.nagp.hotelsservice.dto.HotelWithRoomCountRoomPriceWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.dto.HotelWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.model.Hotel;

public interface HotelService {
	public List<HotelWithoutRoomsData> getAllHotels(HotelSearchParameters hsp);
	public HotelWithRoomCountRoomPriceWithoutRoomsData getHotelByHotelId(String hotelId, HttpServletResponse res);
}
