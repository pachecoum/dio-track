package com.primeiro.spring.service;

import java.lang.Iterable;

import com.primeiro.spring.model.User;

public interface IUserService {

	User createUser(User user);

	User updateUser(User user);
	
	Iterable<User> getAllUser();

	User getUserById(Long id);

	User getUserByUsername(Long username);

	void deleteUserById(Long id);
}
