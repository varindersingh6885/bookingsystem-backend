package com.nagarro.nagp.flightsservice.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nagarro.nagp.flightsservice.dto.FlightSearchParameters;
import com.nagarro.nagp.flightsservice.model.Flight;
import com.nagarro.nagp.flightsservice.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {
	
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
    private CircuitBreakerFactory circuitBreakerFactory;
	
	@Override
	public List<Flight> getAllFlights(FlightSearchParameters fsp) {
		// TODO Auto-generated method stub
		RestTemplate restClient = new RestTemplate();
        
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        
        Flight[] flights = circuitBreaker.run(() -> {
            System.out.println("Attempt");
            List<ServiceInstance> dbFlightsService = discoveryClient.getInstances("DB-FLIGHTS");
            String dbFlightsUri = dbFlightsService.get(0).getUri().toString();
            
            dbFlightsUri += "/flights?";
            if(fsp.getSource() != null) dbFlightsUri += "source=" + fsp.getSource() + "&";
            if(fsp.getDestination() != null) dbFlightsUri += "destination=" + fsp.getDestination() + "&";
            if(fsp.getDepartureDate() != null) dbFlightsUri += "departureDate=" + fsp.getDepartureDate() + "&";
        	
            return restClient.getForObject(dbFlightsUri, Flight[].class);
        }, throwable -> {
//        	System.out.println("db-flights service down");
            return null;
        });
        if(flights == null) {
        	throw new RuntimeException("Service is down currently. Try again Later");
        }
		return new ArrayList<>(Arrays.asList(flights));
	}

	@Override
	public Flight getFlightByFlightId(String flightId) {
		// TODO Auto-generated method stub
		return null;
	}

}
