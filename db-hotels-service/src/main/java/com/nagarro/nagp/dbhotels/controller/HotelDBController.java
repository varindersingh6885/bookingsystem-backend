package com.nagarro.nagp.dbhotels.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.dbhotels.dto.HotelSearchParameters;
import com.nagarro.nagp.dbhotels.dto.OrderHotel;
import com.nagarro.nagp.dbhotels.model.Hotel;
import com.nagarro.nagp.dbhotels.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelDBController {
	
	Logger logger = LogManager.getLogger(HotelDBController.class);
	
	@Autowired
	private HotelService hotelService;
	
	@GetMapping("")
	public List<Hotel> searchFlights(
			@RequestParam(required = false, name = "hotelName") String hotelName,
			@RequestParam(required = false, name = "city") String city,
			@RequestParam(required = false, name = "address") String address
			) {
	
		logger.info("/hotels get hotels db-hotels-service");
		List<Hotel> hotels = hotelService.getAllHotels(new HotelSearchParameters(hotelName, city, address));
		
		return hotels;
	}
	
	@GetMapping("/{id}")
	public Hotel getHotelById(@PathVariable("id") String hotelId, HttpServletResponse res) {
//		System.out.println(flightId);
		
		logger.info("/hotels/{id} get hotel by hotelId db-hotels-service");
		Hotel h = hotelService.getHotelByHotelId(hotelId);
		if(h == null) {
			res.setStatus(404);
		}
		return h;
	}
	
	@PostMapping("/book")
	public OrderHotel bookSeats(@RequestBody OrderHotel booking) {
		
		logger.info("/hotels/book update hotel data");
		OrderHotel bookingUpdate = hotelService.updateRoomStatus(booking);
		
		return bookingUpdate;
	}
}
