package com.primeiro.spring.service;

import java.util.List;

import com.primeiro.spring.ApplicationConfig;
import com.primeiro.spring.exception.AddressNotFoundException;
import com.primeiro.spring.extension.PopulateDatabaseExtension;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.repository.AddressRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import org.junit.jupiter.api.Assertions;

@DataJpaTest
@ContextConfiguration(classes = ApplicationConfig.class)
@ExtendWith({ PopulateDatabaseExtension.class })
@Import(AddressService.class)
class AddressServiceTest {

	private final static Logger log = LoggerFactory.getLogger(AddressServiceTest.class.getName());

	@Autowired
	private AddressService addressService;

	@SpyBean
	private AddressRepository addressRepository;

	@BeforeAll
	static void setup() {
		log.info("Start AddressServiceTest.");
	}

	@BeforeEach
	void init(){
		Mockito.reset(addressRepository);
	}

	@DisplayName("Test create address")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passAddress")
	void testCreateAddress(Address expectedAddress) {
		addressService.createAddress(expectedAddress);

		Mockito.verify(addressRepository).save(expectedAddress);

		Assertions
				.assertTrue(expectedAddress.equals(addressRepository.findById(expectedAddress.getCep()).orElseThrow()));
	}

	@DisplayName("Test select persited data by cep")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedAddress")
	void testGetAddressPersitedByCep(Address expectedAddress) {
		Address address = addressService.getAddressByCep(expectedAddress.getCep());

		Mockito.verify(addressRepository, Mockito.only()).findById(expectedAddress.getCep());

		Assertions.assertTrue(expectedAddress.equals(address));
	}

	@DisplayName("Test get all addresses persisteds")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedAddresses")
	void testGetAllAddresses(List<Address> expectedAddresses) {
		List<Address> persistedAddresses = addressService.getAllAdress();

		Mockito.verify(addressRepository, Mockito.only()).findAll();

		Assertions.assertEquals(expectedAddresses.size(), persistedAddresses.size(),
				"The size between the persisted addresses and payload are diferent");
		Assertions.assertTrue(persistedAddresses.containsAll(expectedAddresses));
		Assertions.assertTrue(expectedAddresses.containsAll(persistedAddresses));
	}

	@DisplayName("Test get a address with a ID that does not exist\n")
	@Test
	void testNotExistAddressWithId() throws Exception {
		Assertions.assertThrows(AddressNotFoundException.class, () -> addressService.getAddressByCep("00000000"));
	}

	@AfterAll
	static void done() {
		log.info("End AddressServiceTest");
	}

}
