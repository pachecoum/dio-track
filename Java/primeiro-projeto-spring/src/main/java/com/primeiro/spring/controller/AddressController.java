package com.primeiro.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.primeiro.spring.dto.AddressDTO;
import com.primeiro.spring.dto.AddressWithUsersDTO;
import com.primeiro.spring.model.Address;
import com.primeiro.spring.service.IAddressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Address", description = "Address operations")
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@Operation(summary = "Create a new address.", description = "Create a new address with this fields.", tags = {
			"Address" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AddressDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid requisition.", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(ref = "/firstspring.yml#/components/schemas/FieldValidation")) }) })
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public AddressDTO postAddress(@NotNull @Valid @RequestBody final AddressDTO addressDto) {

		Address address = AddressDTO.ToEntity(addressDto);
		address = addressService.createAddress(address);

		return AddressDTO.fromEntity(address);
	}

	@Operation(summary = "Fetch address by cep.", description = "Fecth a address with a cep.", tags = { "Address" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AddressWithUsersDTO.class)) }) })
	@GetMapping("/{cep}")
	@ResponseStatus(HttpStatus.OK)
	public AddressWithUsersDTO getAddressByCep(
			@Pattern(regexp = "^[0-9]{5}[0-9]{3}$", message = "Invalid CEP format.") @PathVariable("cep") @Parameter(description = "Cep for fetching.", required = true) final String cep) {
		return AddressWithUsersDTO.fromEntity(addressService.getAddressByCep(cep));
	}

	@Operation(summary = "Fetch with all addresses.", description = "Fetch with all addresses.", tags = { "Address" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success operation", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AddressWithUsersDTO.class))) }) })
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<AddressWithUsersDTO> getAllAddresses() {
		return addressService.getAllAdress().stream()
				.map(AddressWithUsersDTO::fromEntity).collect(Collectors.toList());
	}
}
