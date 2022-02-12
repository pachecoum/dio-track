package com.primeiro.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.primeiro.spring.dto.UserWithAddressDTO;
import com.primeiro.spring.model.User;
import com.primeiro.spring.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "User operations")
public class UserController {

	@Autowired
	private IUserService userService;

	@Operation(summary = "Create new user.", description = "Create a new user with a address.", tags = {
			"User" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User for request", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "/firstspring.yml#/components/schemas/NewUser"))))

	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserWithAddressDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid requisition.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "/firstspring.yml#/components/schemas/FieldValidation")) }) })

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public UserWithAddressDTO postUser(@RequestBody @Valid final UserWithAddressDTO userDto) {

		User user = UserWithAddressDTO.ToEntity(userDto);
		user = userService.createUser(user);
		UserWithAddressDTO userDtoPersisted = UserWithAddressDTO.fromEntity(user);

		return userDtoPersisted;
	}

	@Operation(summary = "Update the user.", description = "Update a user with request body.", tags = {
			"User" }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body for user update", required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "/firstspring.yml#/components/schemas/UpdateUser"))))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserWithAddressDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid requisition.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "/firstspring.yml#/components/schemas/FieldValidation")) }) })
	@PutMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public UserWithAddressDTO updateUser(@RequestBody @Valid final UserWithAddressDTO userDto) {

		User user = UserWithAddressDTO.ToEntity(userDto);
		user = userService.updateUser(user);
		UserWithAddressDTO userDtoPersisted = UserWithAddressDTO.fromEntity(user);

		return userDtoPersisted;
	}

	@Operation(summary = "Delete a user with a user by id.", description = "Delete the user with ID passed.", tags = {
			"User" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = @Content) })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUserById(@NotNull @NotBlank @Param("id") final Long id) {
		userService.deleteUserById(id);
	}

	@Operation(summary = "Fetch with a user by id.", description = "Find a user by id parameter.", tags = { "User" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserWithAddressDTO.class)) }) })
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserWithAddressDTO getUserById(@NotNull @NotBlank @Param("id") final Long id) {
		return UserWithAddressDTO.fromEntity(userService.getUserById(id));
	}

	@Operation(summary = "Fetch user info.", description = "Fetch from viacep many informations about the cep.", tags = {
			"User" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserWithAddressDTO.class))) }) })
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<UserWithAddressDTO> getUsers() {
		List<UserWithAddressDTO> usersDto = new ArrayList<UserWithAddressDTO>();
		Iterable<User> users = userService.getAllUser();

		for (User u : users) {
			usersDto.add(UserWithAddressDTO.fromEntity(u));
		}

		return usersDto;
	}
}
