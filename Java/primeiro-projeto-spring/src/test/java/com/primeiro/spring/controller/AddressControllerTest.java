package com.primeiro.spring.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.primeiro.spring.ApplicationConfig;
import com.primeiro.spring.dto.AddressWithUsersDTO;
import com.primeiro.spring.dto.ExceptionDTO;
import com.primeiro.spring.exception.AddressNotFoundException;
import com.primeiro.spring.dto.AddressDTO;
import com.primeiro.spring.extension.PopulateDatabaseExtension;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.repository.AddressRepository;
import com.primeiro.spring.service.AddressService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Tag("controller")
@SpringBootTest(classes = ApplicationConfig.class)
@Transactional
@AutoConfigureMockMvc
@ExtendWith({ PopulateDatabaseExtension.class })
class AddressControllerTest {

	private final static Logger log = LoggerFactory.getLogger(AddressControllerTest.class);

	@Autowired
	private MockMvc mvc;

	@SpyBean
	private AddressService addresseService;

	@Autowired
	private AddressRepository addressRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void init() {
		Mockito.reset(addresseService);
	}

	@Tag("integration")
	@DisplayName("Test create a new address")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passAddressDTO")
	void testCreateAddress(AddressDTO newAddressDTO) throws Exception {
		byte[] requestContent = mapper.writeValueAsBytes(newAddressDTO);

		byte[] responseContent = mvc
				.perform(post("/addresses/").content(requestContent).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse()
				.getContentAsByteArray();

		Mockito.verify(addresseService, Mockito.times(1)).createAddress(Mockito.any(Address.class));

		Assertions.assertArrayEquals(requestContent, responseContent);

		Address expectedAddress = AddressDTO.ToEntity(newAddressDTO);

		Optional<Address> persistedAddress = addressRepository.findById(expectedAddress.getCep());

		Assertions.assertTrue(persistedAddress.isPresent(), "Address no present in database");
		Assertions.assertTrue(expectedAddress.equals(persistedAddress.get()));
	}

	@Tag("integration")
	@DisplayName("Test get all addresses")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedAddressesWithUsersDTO")
	void testGetAllAddressOk(List<AddressWithUsersDTO> expectedAddressesDTO) throws Exception {

		byte[] expectedJsonResponse = mapper.writeValueAsBytes(expectedAddressesDTO);
		mvc.perform(get("/addresses/")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().bytes(expectedJsonResponse));

		Mockito.verify(addresseService, Mockito.times(1)).getAllAdress();
	}

	@Tag("integration")
	@DisplayName("Test get address by cep")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedAddressWithUsersDTO")
	void testGetAddressByCep(AddressWithUsersDTO expectedAddressDTO) throws Exception {

		byte[] expectedJsonResponse = mapper.writeValueAsBytes(expectedAddressDTO);

		mvc.perform(get("/addresses/" + expectedAddressDTO.getCep()))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().bytes(expectedJsonResponse));

		Mockito.verify(addresseService, Mockito.times(1)).getAddressByCep(expectedAddressDTO.getCep());
	}

	@Tag("integration")
	@DisplayName("Test get address with not exists by cep")
	@Test
	void testGetAddressNotExistsByCep() throws Exception {
		String path = "/addresses/" + "00000000";
		Exception raisedException = new AddressNotFoundException();
		ExceptionDTO responseException = new ExceptionDTO(raisedException.getMessage());
		responseException.setPath(path);
		byte[] responseContent = mapper.writeValueAsBytes(responseException);

		mvc.perform(get(path)).andDo(print()).andExpect(status().isNotFound())
				.andExpect(content().bytes(responseContent));

		Mockito.verify(addresseService, Mockito.times(1)).getAddressByCep(path.replace("/addresses/", ""));
	}

	@Test
	@Disabled("Test not implemented")
	void testPutAddressOk() {
		log.info("Not implemented");
	}
}
