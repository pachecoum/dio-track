package com.primeiro.spring.model;

import java.util.Objects;

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

	public User(){}

    public User(User user){
        this(user.getName(), user.getUsername());
    }

    public User(Long id, String name,String username){
        this(name, username);
        this.id = id;
    }

    public User(String name,String username){
        this.name = name;
        this.username = username;
    }


    public User(String name,String username, Address address){
        this.name = name;
        this.username = username;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (id == null? 0 : id.hashCode());
		hash = 31 * hash + (name == null? 0 : name.hashCode());
		hash = 31 * hash + (username == null? 0 : username.hashCode());
		hash = 31 * hash + (address == null? 0 : address.hashCode());

		// or
		hash *= Objects.hash(id, name, username, address);

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (this.getClass() != o.getClass()) return false;

		User user = (User)o;
        return id.equals(user.getId()) && name.equals(user.getName()) 
            && username.equals(user.getUsername()) && (address == null ? address == user.getAddress(): address.equals(user.getAddress()));
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
