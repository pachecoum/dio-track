package com.primeiro.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.primeiro.spring.exception.NotImplementedException;
import com.primeiro.spring.exception.UserNotFoundException;
import com.primeiro.spring.extension.PopulateDatabaseExtension;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.model.User;
import com.primeiro.spring.repository.UserRepository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.primeiro.spring.ApplicationConfig;

@DataJpaTest
@ContextConfiguration(classes = ApplicationConfig.class)
@Import({ UserService.class, ApplicationConfig.class })
@ExtendWith({ PopulateDatabaseExtension.class })
class UserServiceTest {

	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class.getName());

	@Autowired
	private UserService userService;

	@SpyBean
	private UserRepository userRepository;

	@BeforeAll
	static void setup() {
		log.info("@BeforeAll - executes once before all test method in this class.");
	}

	@BeforeEach
	void init() {
		log.info("@BeforeEach - executes before each test.");
		Mockito.reset(userRepository);
	}

	@DisplayName("Test create a new user with cep that exists")
	@ParameterizedTest(name = "Test with {arguments}")
	@MethodSource("com.primeiro.spring.Fixtures#passUserPersistedAddress")
	void testAddNewUser_withCepExists(User user, Address persistedAddress) throws Exception {
		user.setAddress(persistedAddress);

		userRepository.save(user);
		Mockito.verify(userRepository, Mockito.times(1)).save(user);

		Assertions.assertTrue(userRepository.findById(user.getId()).isPresent());
	}

	@DisplayName("Test create a new user with cep with not exists, then with use of feign client")
	@ParameterizedTest(name = "Test with {arguments}")
	@MethodSource("com.primeiro.spring.Fixtures#passUserAddress")
	void testAddNewUser_withCepNotExists_thenNeedUseFeignClient(User user, Address address) throws Exception {
		user.setAddress(address);

		User expectedUser = userService.createUser(user);

		Mockito.verify(userRepository, Mockito.times(1)).save(user);

		Assertions.assertTrue(expectedUser.equals(userRepository.findById(expectedUser.getId()).orElseThrow()));
	}

	@DisplayName("Test get user by username")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUser")
	void testGetUserByUsername(User expectedUser) {
		try {
			User persistedUser = userService.getUserByUsername(expectedUser.getUsername());

			Mockito.verify(userRepository, Mockito.times(1)).findUsernameNotId(expectedUser.getId(),
					expectedUser.getUsername());

			Assertions.assertTrue(expectedUser.equals(persistedUser));
		} catch (NotImplementedException exception) {
			Assumptions.assumeTrue(false, "Not implemented method");
		} catch (Exception exception) {
			throw exception;
		}
	}

	@DisplayName("Test delete user by id")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUser")
	void testDeleteUserById(User expectedUser) {
		userRepository.deleteById(expectedUser.getId());
		Mockito.verify(userRepository, Mockito.only()).deleteById(expectedUser.getId());
		Assertions.assertFalse(userRepository.findById(expectedUser.getId()).isPresent());
	}

	@DisplayName("Test find if all users are populated are in database")
	@ParameterizedTest(name = "{0} tested")
	@MethodSource("com.primeiro.spring.Fixtures#passPersistedUsers")
	void testFindAllUsers(List<User> expectedUsers) {
		List<User> persistedUsers = userService.getAllUser();

		Mockito.verify(userRepository, Mockito.only()).findAll();
		Assertions.assertEquals(expectedUsers.size(), persistedUsers.size(),
				"The size between the persisted addresses and payload are diferent");
		Assertions.assertTrue(persistedUsers.containsAll(expectedUsers));
		Assertions.assertTrue(expectedUsers.containsAll(persistedUsers));
	}

	@DisplayName("Test get a user with a ID that does not exist\n")
	@Test
	void testNotExistUserWithId() throws Exception {
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(0L));
	}

	@AfterEach
	void tearDown() {
		log.info("@AfterEach - executed after each test.");
		List<User> persistedUsers = userService.getAllUser();
		persistedUsers.size();
	}

	@AfterAll
	static void done() {
		log.info("@AfterAll - executed once after all test method in this class.");
	}
}
