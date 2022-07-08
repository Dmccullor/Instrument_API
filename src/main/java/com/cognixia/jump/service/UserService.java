package com.cognixia.jump.service;

import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;

@Service
public class UserService {
	
	public User updateUser(User user) {
		
		User updated = new User(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isEnabled());
		
		return updated;
		
	}

}
