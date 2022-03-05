package com.primeiro.spring.service;

import java.util.List;

import com.primeiro.spring.model.User;
import com.primeiro.spring.exception.NotImplementedException;

public interface IUserService {

	User createUser(User user);

	User updateUser(User user);
	
	List<User> getAllUser();

	User getUserById(Long id);

	default User getUserByUsername(String username){
		throw new NotImplementedException();
	}

	void deleteUserById(Long id);
}
