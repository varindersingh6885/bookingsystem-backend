package com.nagarro.nagp.bookingservice.repository.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.bookingservice.model.OrderHotel;
import com.nagarro.nagp.bookingservice.repository.OrderHotelRepository;

@Repository
public class OrderHotelRepositoryImpl implements OrderHotelRepository {

	HashMap<String, OrderHotel> hotelOrders;
	
	public OrderHotelRepositoryImpl() {
		hotelOrders = new HashMap<>();
	}

	@Override
	public OrderHotel addHotelOrder(OrderHotel order) {
		hotelOrders.put(order.getBookingId(), order);
		return order;
	}

	@Override
	public OrderHotel getHotelOrder(String bookingId) {
		if(hotelOrders.containsKey(bookingId)) {
			return hotelOrders.get(bookingId);
		}
		return null;
	}

	@Override
	public OrderHotel updateHotelOrder(OrderHotel order) {
		hotelOrders.put(order.getBookingId(), order);
		return order;
	}

}
