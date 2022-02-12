package com.primeiro.spring.repository;


import org.springframework.data.repository.CrudRepository;

import com.primeiro.spring.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
	

}


