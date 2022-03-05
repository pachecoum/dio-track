package com.primeiro.spring.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.primeiro.spring.model.Address;
import com.primeiro.spring.repository.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.primeiro.spring.exception.AddressNotFoundException;

@Service
public class AddressService implements IAddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Address createAddress(Address address) {
		return addressRepository.save(address);
	}
	
	public List<Address> getAllAdress() {
		return StreamSupport.stream(addressRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public Address getAddressByCep(String cep) {
		return addressRepository.findById(cep).orElseThrow(() -> new AddressNotFoundException());
	}

}
