package com.primeiro.spring.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity(name = "users")
@Table(name = "users")
@Schema(hidden = true)
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = false)
    @Size(min = 2, max = 50)
	private String name;

	@Column(nullable = false, unique = true)
    @Size(min = 6, max = 50)
	private String username;

    @ManyToOne
    @JoinColumn(name = "address_cep")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
