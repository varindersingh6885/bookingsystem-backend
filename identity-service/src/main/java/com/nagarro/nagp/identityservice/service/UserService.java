package com.nagarro.nagp.identityservice.service;

import javax.servlet.http.HttpServletResponse;

import com.nagarro.nagp.identityservice.dto.UserCredentials;
import com.nagarro.nagp.identityservice.model.User;

public interface UserService {
	public User addUser(User user);
	public String getValidToken(UserCredentials uc, HttpServletResponse res);
}
