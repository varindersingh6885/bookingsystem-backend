package com.nagarro.nagp.dbflights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DbFlightsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbFlightsServiceApplication.class, args);
	}

}
