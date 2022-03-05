package com.primeiro.spring.service;

import java.util.List;
import java.util.ArrayList;

import com.primeiro.spring.repository.UserRepository;
import com.primeiro.spring.repository.AddressRepository;
import com.primeiro.spring.model.User;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.extern.ViaCepClient;
import com.primeiro.spring.exception.UserNotFoundException;

import com.primeiro.spring.exception.AddressNotFoundException;
import com.primeiro.spring.exception.UsernameIsUsedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	ViaCepClient viaCepClient;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	Address validateAddress(User user) {
		return  addressRepository.findById(user.getAddress().getCep()).orElseGet(() -> {
			Address newAddress = viaCepClient.getCep(user.getAddress().getCep());	
			if(newAddress.getCep() == null)
				throw new AddressNotFoundException();

			return addressRepository.save(newAddress);
		});
	}

	public User createUser(User user) {
		validateUsername(user.getId(), user.getUsername());
		Address address = validateAddress(user);
		user.setAddress(address);
		return userRepository.save(user);
	}

	public User updateUser(User user) {
		validateUsername(user.getId(), user.getUsername());
		Address address = validateAddress(user);
		user.setAddress(address);
		return userRepository.save(user);	
	}
	
	public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		userRepository.findAll().iterator().forEachRemaining(users::add);
			
		return users;
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	private void validateUsername(Long id, String username) {
		if(userRepository.findUsernameNotId(id, username)) {
			throw new UsernameIsUsedException();
		}
	}
}


