package com.primeiro.spring.dto;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.primeiro.spring.model.User;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public class UserWithAddressDTO extends UserDTO{

	@Valid
    @JsonProperty("address")
	private AddressDTO address;

	public static UserWithAddressDTO fromEntity(User user) {
		UserWithAddressDTO userDto = modelMapper.map(user, UserWithAddressDTO.class);
		userDto.setAddress(AddressDTO.fromEntity(user.getAddress()));

		return userDto;
	}

	public static User ToEntity(UserWithAddressDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		user.setAddress(AddressDTO.ToEntity(userDto.getAddress()));

		return user;
	}

    @Schema(implementation = AddressDTO.class)
    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

}
