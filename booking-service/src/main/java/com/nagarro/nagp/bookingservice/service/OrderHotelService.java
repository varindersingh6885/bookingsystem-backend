package com.nagarro.nagp.bookingservice.service;

import com.nagarro.nagp.bookingservice.dto.NewHotelOrder;
import com.nagarro.nagp.bookingservice.model.OrderHotel;

public interface OrderHotelService {
	OrderHotel createHotelOrder(NewHotelOrder order);
	OrderHotel getHotelOrder(String bookingId);
}
