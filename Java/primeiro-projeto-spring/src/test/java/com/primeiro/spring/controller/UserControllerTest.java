package com.primeiro.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
//import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeiro.spring.ApplicationConfig;
import com.primeiro.spring.dto.UserWithAddressDTO;
import com.primeiro.spring.extension.PopulateDatabaseExtension;
import com.primeiro.spring.model.User;
import com.primeiro.spring.repository.UserRepository;
import com.primeiro.spring.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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

@Tag("controller")
@SpringBootTest(classes = ApplicationConfig.class)
@Transactional
@AutoConfigureMockMvc
@ExtendWith({ PopulateDatabaseExtension.class })
class UserControllerTest {

	private static Logger log = LoggerFactory.getLogger(UserControllerTest.class);

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@SpyBean
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void init() {
		Mockito.reset(userService);
		log.info("Before test");
	}

	@Tag("integration")
	@DisplayName("Test create a new user")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passUserWithAddressDTO")
	void testCreateNewUser(UserWithAddressDTO userDto) throws Exception {
		byte[] requestRaw = mapper.writeValueAsBytes(userDto);
		mvc.perform(post("/users/").content(requestRaw).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().bytes(requestRaw));

		Mockito.verify(userService, Mockito.times(1)).createUser(Mockito.any(User.class));

		Assertions.assertTrue(userRepository.existsById(userDto.getId()));
	}

	@Tag("integration")
	@DisplayName("Test update user")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUserWithAddressDTO")
	void testUpdateUser(UserWithAddressDTO userDto) throws Exception {
		String newUsername = "new."+userDto.getUsername();
		userDto.setUsername(newUsername);
		byte[] requestRaw = mapper.writeValueAsBytes(userDto);
		mvc.perform(put("/users/").content(requestRaw).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().bytes(requestRaw));

		Mockito.verify(userService, Mockito.times(1)).updateUser(Mockito.any(User.class));

		Assertions.assertTrue(userRepository.findById(userDto.getId()).orElseThrow().getUsername().equals(newUsername));
	}

	@Tag("integration")
	@DisplayName("Test delete user")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUserWithAddressDTO")
	void testDeleteUser(UserWithAddressDTO userDto) throws Exception {

		mvc.perform(delete("/users/" + userDto.getId())).andDo(print())
				.andExpect(status().isOk());

		Mockito.verify(userService, Mockito.times(1)).deleteUserById(userDto.getId());

		Assertions.assertFalse(userRepository.existsById(userDto.getId()));
	}

	@Tag("integration")
	@DisplayName("Test get user by ID")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUserWithAddressDTO")
	void testGetUserById(UserWithAddressDTO userDto) throws Exception {

		byte[] requestRaw = mapper.writeValueAsBytes(userDto);

		mvc.perform(get("/users/"+userDto.getId())).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().bytes(requestRaw));

		Mockito.verify(userService, Mockito.times(1)).getUserById(userDto.getId());
	}

	@Tag("integration")
	@DisplayName("Test get all ")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUsersWithAddressDTO")
	void testGetAllAddressOk(List<UserWithAddressDTO> expectedUsers) throws Exception {
		// Collections.swap(expectedUsers, 1, 0);
		String expectedJsonResponse = mapper.writeValueAsString(expectedUsers);
		String res = mvc.perform(get("/users/")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(expectedJsonResponse)).andReturn().getResponse()
				.getContentAsString(StandardCharsets.UTF_8); // Se não definir o charset como utf-8 vai da erro na
																// comparação se caso ouver caracteres não ASCII
		Assertions.assertEquals(expectedJsonResponse, res); // Diferente do content().json(expectedJsonResponse), nesse
															// caso a ordem importa, basta descomentar a primeira linha
		Mockito.verify(userService, Mockito.times(1)).getAllUser();
	}

	@Tag("integration")
	@DisplayName("Test bad request to create a user")
	@ParameterizedTest(name = "{arguments} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passUserWithAddressDTO")
	void testBadRequestCreateNewUser(UserWithAddressDTO userDto) throws Exception {
		userDto.setName("");
		byte[] requestRaw = mapper.writeValueAsBytes(userDto);
		Map<String, String> badResponse = new HashMap<String, String>();
		badResponse.put("name", "Size must be between 2 and 50.");
		badResponse.put("path", "/users/");
		String expectedExceptionResponse = mapper.writeValueAsString(badResponse);
		mvc.perform(post("/users/").content(requestRaw).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(expectedExceptionResponse));

		Mockito.verify(userService, Mockito.times(0)).createUser(Mockito.any(User.class));

		Assertions.assertFalse(userRepository.existsById(userDto.getId()));
	}
}
