package com.nagarro.nagp.identityservice.repository.impl;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.nagarro.nagp.identityservice.model.User;
import com.nagarro.nagp.identityservice.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	HashMap<String, User> users;
	
	public UserRepositoryImpl() {
		users = new HashMap<>();
		createDummyUsers();
	}
	
	@Override
	public User addUser(User user) {
		// todo - validate all fields are present
		
		users.put(user.getUsername(), user);
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		if(users.containsKey(username)) {
			return users.get(username);
		}
		return null;
	}
	
	private void createDummyUsers() {
		User u1 = new User("varindersingh@gmail.com", "varinder123", "Varinder", "Singh");
		User u2 = new User("test@gmail.com", "test@123", "Test", "Name");
		
		users.put(u1.getUsername(), u1);
		users.put(u2.getUsername(), u2);
	}

}
