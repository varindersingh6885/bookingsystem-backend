package com.nagarro.nagp.bookingservice.repository;

import com.nagarro.nagp.bookingservice.model.OrderHotel;

public interface OrderHotelRepository {
	OrderHotel addHotelOrder(OrderHotel order);
	OrderHotel getHotelOrder(String bookingId);
	OrderHotel updateHotelOrder(OrderHotel order);
}
