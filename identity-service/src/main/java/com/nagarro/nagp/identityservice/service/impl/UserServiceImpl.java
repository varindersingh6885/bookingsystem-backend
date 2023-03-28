package com.nagarro.nagp.identityservice.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.identityservice.dto.UserCredentials;
import com.nagarro.nagp.identityservice.model.User;
import com.nagarro.nagp.identityservice.repository.UserRepository;
import com.nagarro.nagp.identityservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User addUser(User user) {
		// TODO - validate all values fields are present
		
		User u = userRepo.addUser(user);
		return u;
	}

	@Override
	public String getValidToken(UserCredentials uc, HttpServletResponse res) {
		// TODO Auto-generated method stub
		User u = userRepo.getUserByUsername(uc.getUsername());
		
		if(u != null) {
			if(uc.getPassword().equals(u.getPassword())) {
				return "validToken123";
			} else {
				res.setStatus(401);
				return "invaild password";
			}
		}
		return "invalid credentials";
	}

}
