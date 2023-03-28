package com.nagarro.nagp.flightsservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.flightsservice.dto.FlightSearchParameters;
import com.nagarro.nagp.flightsservice.dto.FlightWithoutSeatsData;
import com.nagarro.nagp.flightsservice.model.Flight;
import com.nagarro.nagp.flightsservice.service.FlightService;

@RestController
@RequestMapping("/flights")
public class FlightController {
	
	
	@Autowired
	private FlightService flightService;
	
	@GetMapping("")
	public List<FlightWithoutSeatsData> searchFlights(
			@RequestParam(required = false, name = "source") String source,
			@RequestParam(required = false, name = "destination") String destination,
			@RequestParam(required = false, name = "departureDate") String departureDate,
			HttpServletResponse res
			) {
		List<FlightWithoutSeatsData> flights = null;
		try {
			flights = flightService.getAllFlights(new FlightSearchParameters(source, destination, departureDate));
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			res.setStatus(500);
		}
		
		return flights;
	}
	
	@GetMapping("/{id}")
	public Flight getFlightById(@PathVariable("id") String flightId) {
		Flight f = flightService.getFlightByFlightId(flightId);
		return f;
	}
	
	
}
