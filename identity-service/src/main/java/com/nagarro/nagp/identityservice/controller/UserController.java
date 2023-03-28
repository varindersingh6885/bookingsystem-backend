package com.nagarro.nagp.identityservice.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private UserService userService;
	
	@PostMapping("")
	public String registerNewUser(@RequestBody User user) {
		
		User newUser = userService.addUser(user);
		
		return newUser.getFirstName() + " is added successfully";
	}

	@PostMapping("/login")
	public String getValidToken(@RequestBody UserCredentials uc, HttpServletResponse res) {
		System.out.println("reached here");
		return userService.getValidToken(uc, res);
	}
}
