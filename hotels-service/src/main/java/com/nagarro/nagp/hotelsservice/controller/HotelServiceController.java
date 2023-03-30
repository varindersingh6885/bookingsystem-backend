package com.nagarro.nagp.hotelsservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.hotelsservice.dto.HotelSearchParameters;
import com.nagarro.nagp.hotelsservice.dto.HotelWithRoomCountRoomPriceWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.dto.HotelWithoutRoomsData;
import com.nagarro.nagp.hotelsservice.model.Hotel;
import com.nagarro.nagp.hotelsservice.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelServiceController {
	
	Logger logger = LogManager.getLogger(HotelServiceController.class);
	
	@Autowired
	private HotelService hotelService;
	
	@GetMapping("")
	public List<HotelWithoutRoomsData> searchHotels(
			@RequestParam(required = false, name = "hotelName") String hotelName,
			@RequestParam(required = false, name = "city") String city,
			@RequestParam(required = false, name = "address") String address,
			HttpServletResponse res
			) {
		
		logger.info("/hotels search hotels");
		
		List<HotelWithoutRoomsData> hotels = null;
		try {
			hotels = hotelService.getAllHotels(new HotelSearchParameters(hotelName, city, address));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
			res.setStatus(500);
		}
		
		return hotels;
	}
	
	@GetMapping("/{id}")
	public HotelWithRoomCountRoomPriceWithoutRoomsData getHotelById(@PathVariable("id") String hotelId, HttpServletResponse res) {
		
		logger.info("/hotels/{id} get hotel by hotelID");
		
		HotelWithRoomCountRoomPriceWithoutRoomsData h = hotelService.getHotelByHotelId(hotelId, res);
		return h;
	}
	
	
}
