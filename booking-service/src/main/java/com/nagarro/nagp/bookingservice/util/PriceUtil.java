package com.nagarro.nagp.bookingservice.util;

import java.util.List;

import com.nagarro.nagp.bookingservice.constants.HotelRoomPrice;

public class PriceUtil {
	public static float calAmount(List<Integer> seatNumbers) {
		float totalAmount = 0;
		for(Integer seatNum : seatNumbers) {
			if(seatNum <= 20) {
				totalAmount += 1000;
			} else {
				totalAmount += 2500;
			}
		}
		return totalAmount;
	}

	public static float calAmount(int roomsRequired) {
		float singleRoomPrice = HotelRoomPrice.LUXURY_PRICE; // considering only one price
		return roomsRequired * singleRoomPrice;
	}
}
