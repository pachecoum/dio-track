package com.primeiro.spring.service;

import java.util.List;

import com.primeiro.spring.model.Address;

public interface IAddressService {

	Address createAddress(Address address);
	
	List<Address> getAllAdress();

	Address getAddressByCep(String cep);
}
