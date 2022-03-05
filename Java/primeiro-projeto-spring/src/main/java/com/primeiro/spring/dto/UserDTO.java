package com.primeiro.spring.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.media.Schema;

import com.primeiro.spring.service.IUserService;
import com.primeiro.spring.model.User;

@Schema(name = "SimpleUser")
public class UserDTO{

	static protected ModelMapper modelMapper = new ModelMapper();

	@Autowired
	static protected IUserService userService;

    @JsonProperty("id")
	private Long id;

    @JsonProperty("name")
    @NotNull(message = "This field must be not null")
    @Size(min = 2, max = 50, message = "Size must be between 2 and 50.")
	private String name;

    @JsonProperty("username")
    @NotNull(message = "This field must be not null")
    @Size(min = 2, max = 50, message = "Size must be between 2 and 50.")
	private String username;

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (id == null? 0 : id.hashCode());
		hash = 31 * hash + (name == null? 0 : name.hashCode());
		hash = 31 * hash + (username == null? 0 : username.hashCode());

		// or
		hash *= Objects.hash(id, name, username);

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o instanceof UserDTO) return false;

		UserDTO user = (UserDTO)o;
        return id.equals(user.getId()) && name.equals(user.getName()) 
            && username.equals(user.getUsername());
	}

	public static UserDTO fromEntity(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);

		return userDto;
	}

	public static User ToEntity(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);

        if(user.getId() != null) {
            User dbUser = userService.getUserById(user.getId());
            user.setAddress(dbUser.getAddress());
        }

		return user;
	}

    /**
     * Get id
     *
     * @return id
     */
    @Schema(example = "1", description = "")

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get name
     *
     * @return name 
     */
    @Schema(example = "Marcos Uchoa", description = "")

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get username 
     *
     * @return username
     */
    @Schema(example = "marcospa", description = "")

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
