package com.nagarro.nagp.identityservice.repository;

import com.nagarro.nagp.identityservice.model.User;

public interface UserRepository {
	public User addUser(User user);
	public User getUserByUsername(String username);
}
