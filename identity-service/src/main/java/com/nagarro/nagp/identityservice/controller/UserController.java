package com.nagarro.nagp.identityservice.controller;

import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.identityservice.dto.UserCredentials;
import com.nagarro.nagp.identityservice.model.User;
import com.nagarro.nagp.identityservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public String registerNewUser(@RequestBody User user, HttpServletResponse res) {
		
		User newUser = userService.addUser(user);
		logger.info("/users Creating new user");
		
		res.setStatus(201);
		return newUser.getFirstName() + " is added successfully";
	}

	@PostMapping("/login")
	public String getValidToken(@RequestBody UserCredentials uc) {
//		System.out.println("validating user");
		logger.info("/users/login Validate user for login");
		return userService.getValidToken(uc);
	}
	
}
