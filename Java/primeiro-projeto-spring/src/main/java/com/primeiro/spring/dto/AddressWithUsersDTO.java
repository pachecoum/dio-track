package com.primeiro.spring.dto;

import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import com.primeiro.spring.service.IAddressService;
import com.primeiro.spring.model.Address;


@Schema(name = "Address")
public class AddressWithUsersDTO extends AddressDTO {

    @Autowired
    private static IAddressService addressService;

    @JsonProperty("users")
	private List<UserDTO> users;

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 31 * hash + (users == null? 0 : users.hashCode());

		// or
		hash *= Objects.hash(super.hashCode(), users.hashCode());

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (this.getClass() != o.getClass()) return false;

		AddressWithUsersDTO address = (AddressWithUsersDTO)o;
    
		return super.equals(o) && users.containsAll(address.getUsers());
	}

	/**
	 * Get users 
	 *
	 * @return users
	 */ 

	public static AddressWithUsersDTO fromEntity(Address address) {
        AddressWithUsersDTO addressDto = modelMapper.map(address, AddressWithUsersDTO.class);

        addressDto.setUsers(address.getUsers().stream().map(UserDTO::fromEntity).collect(Collectors.toList()));

        return addressDto;
	}

	public static Address ToEntity(AddressWithUsersDTO addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);

        if(address.getCep() != null) {
            Address persistedAddress = addressService.getAddressByCep(address.getCep());
            address.setUsers(persistedAddress.getUsers());
        }

		return address;
	}

    @ArraySchema(schema = @Schema(implementation = UserDTO.class))
	public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
		
}
