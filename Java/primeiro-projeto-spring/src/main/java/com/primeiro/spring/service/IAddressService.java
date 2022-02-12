package com.primeiro.spring.service;

import java.lang.Iterable;

import com.primeiro.spring.model.Address;

public interface IAddressService {

	Address createAddress(Address address);
	
	Iterable<Address> getAllAdress();

	Address getAddressByCep(String cep);
}
