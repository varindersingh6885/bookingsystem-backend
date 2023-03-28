package com.nagarro.nagp.identityservice.service;


import com.nagarro.nagp.identityservice.dto.UserCredentials;
import com.nagarro.nagp.identityservice.model.User;

public interface UserService {
	public User addUser(User user);
	public String getValidToken(UserCredentials uc);
}
