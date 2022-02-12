package com.primeiro.spring.service;

import com.primeiro.spring.repository.UserRepository;
import com.primeiro.spring.repository.AddressRepository;
import com.primeiro.spring.model.User;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.extern.ViaCepClient;
import com.primeiro.spring.exception.UserNotFoundException;
import com.primeiro.spring.exception.AddressNotFoundException;
import com.primeiro.spring.exception.UsernameIsUsedException;
import com.primeiro.spring.exception.NotImplementedException;

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

	private Address validateAddress(User user) {

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
	
	public Iterable<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
	}

	public User getUserByUsername(Long username) {
		throw new NotImplementedException();
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


























