package com.nagarro.nagp.dbhotels.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.dbhotels.model.Hotel;
import com.nagarro.nagp.dbhotels.repository.HotelRepository;

@Repository
public class HotelRepositoryImpl implements HotelRepository {
	List<Hotel> hotels;
	HashMap<String, Integer> hotelsIndex;
	
	public HotelRepositoryImpl() {
		this.hotels = new ArrayList<>();
		this.hotelsIndex = new HashMap<>();
		createDummyHotels();
	}
	
	@Override
	public List<Hotel> getAllHotels() {
		return hotels;
	}

	@Override
	public Hotel getHotelByHotelID(String hotelId) {
		for(int i = 0; i < hotels.size(); i++) {
			if(hotels.get(i).getHotelId().equals(hotelId))
				return hotels.get(i);
		}
		return null;
	}

	@Override
	public Hotel updateRoomStatus(String hotelId, int roomNumber, String dateOccupied) {
		Hotel h = hotels.get(hotelsIndex.get(hotelId));
		h.getRooms().get(roomNumber).getDatesOccupied().add(dateOccupied);
		return h;
	}
	
	
	public void createDummyHotels() {
		Hotel h1 = new Hotel("RD1234","Radison", "Gurugram", "Gurugram Sector-30" ,new ArrayList<>());
		Hotel h2 = new Hotel("HY4139","Hyatt", "Amritsar", "Ranjit Avenue, Amritsar", new ArrayList<>());
		Hotel h3 = new Hotel("JW8625","JW Mariot", "Delhi", "Sector 24, Delhi", new ArrayList<>());
		Hotel h4 = new Hotel("CI3731","Country Inn", "Mumbai", "Model Town, Mumbai", new ArrayList<>());
		Hotel h5 = new Hotel("SK3288","Sky Hotel", "Chandigarh", "Sector 32, Chandigarh",new ArrayList<>());
		Hotel h6 = new Hotel("JJ2904","JJ Hotel", "Amritsar", "Hall Gate, Amritsar",new ArrayList<>());
		
		
		hotels.add(h1);
		hotelsIndex.put(h1.getHotelId(), 0);
		hotels.add(h2);
		hotelsIndex.put(h2.getHotelId(), 1);
		hotels.add(h3);
		hotelsIndex.put(h3.getHotelId(), 2);
		hotels.add(h4);
		hotelsIndex.put(h4.getHotelId(), 3);
		hotels.add(h5);
		hotelsIndex.put(h5.getHotelId(), 4);
		hotels.add(h6);
		hotelsIndex.put(h6.getHotelId(), 5);
	}

}
