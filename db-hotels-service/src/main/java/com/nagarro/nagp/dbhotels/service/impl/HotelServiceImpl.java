package com.nagarro.nagp.dbhotels.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.dbhotels.dto.HotelSearchParameters;
import com.nagarro.nagp.dbhotels.dto.OrderHotel;
import com.nagarro.nagp.dbhotels.dto.OrderStatus;
import com.nagarro.nagp.dbhotels.model.Hotel;
import com.nagarro.nagp.dbhotels.model.HotelRoom;
import com.nagarro.nagp.dbhotels.repository.HotelRepository;
import com.nagarro.nagp.dbhotels.service.HotelService;
import com.nagarro.nagp.dbhotels.util.CustomDateUtil;

@Service
public class HotelServiceImpl implements HotelService {

	
	@Autowired
	private HotelRepository hotelRepo;
	
	@Override
	public List<Hotel> getAllHotels(HotelSearchParameters hsp) {
		List<Hotel> allHotels = hotelRepo.getAllHotels();

		if(hsp.getHotelName() == null && 
				hsp.getCity() == null && 
				hsp.getAddress() == null
		) {
			return allHotels;
		}
		List<Hotel> currentHotels = allHotels;
		List<Hotel> filteredHotels = new ArrayList<>();
		
		// filter by source
		if(hsp.getHotelName() != null) {
			for(Hotel h : currentHotels) {
				if(h.getHotelName().toLowerCase().equals(hsp.getHotelName().toLowerCase())) {
					filteredHotels.add(h);
				}
			}
			currentHotels = filteredHotels;
		} 
		
		
		// filter by destination
		if(hsp.getCity() != null) {
			filteredHotels = new ArrayList<>();
			for(Hotel h : currentHotels) {
				if(h.getCity().toLowerCase().equals(hsp.getCity().toLowerCase())) {
					filteredHotels.add(h);
				}
			}
			currentHotels = filteredHotels;
		}
		
		
		// filter by date
		if(hsp.getAddress() != null) {
			filteredHotels = new ArrayList<>();
			for(Hotel h : currentHotels) {
				if(h.getAddress().toLowerCase().equals(hsp.getAddress().toLowerCase())) {
					filteredHotels.add(h);
				}
			}
		}
		
		return filteredHotels;
	}

	@Override
	public Hotel getHotelByHotelId(String hotelId) {
		return hotelRepo.getHotelByHotelID(hotelId);
	}

	@Override
	public OrderHotel updateRoomStatus(OrderHotel booking) {
		String hotelId = booking.getHotelId();
		int roomsRequired = booking.getRoomsRequired();
		
		Hotel h = hotelRepo.getHotelByHotelID(hotelId);
		
		List<String> datesRequired = CustomDateUtil.getIndividualDates(booking.getCheckInDate(), booking.getCheckOutDate());

		int roomsAvailableCount = 0;
		List<HotelRoom> roomsAvailableList = new ArrayList<>();
		for(HotelRoom room : h.getRooms()) {
			boolean isVacant = true;
			for(String dateRequired : datesRequired) {
				if(room.getDatesOccupied().contains(dateRequired)) {
					isVacant = false;
					break;
				}
			}
			if(isVacant) {
				roomsAvailableList.add(room);
				roomsAvailableCount++;
			}
		}
		if(roomsAvailableCount >= roomsRequired) {
			for(int i = 0; i < roomsRequired; i++) {
				roomsAvailableList.get(i).getDatesOccupied().addAll(datesRequired);
				booking.getRoomsNumbersAllocated().add(roomsAvailableList.get(i).getRoomNumber());
			}
			booking.setOrderStatus(OrderStatus.CONFIRMED);
		} else {
			booking.setOrderStatus(OrderStatus.UNCONFIRMED);
			booking.setRemarks("Booking Failed! Rooms not available");
		}
		return booking;
	}

}
