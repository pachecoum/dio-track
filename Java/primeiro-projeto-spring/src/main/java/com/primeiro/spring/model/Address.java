package com.primeiro.spring.model;

import java.util.List;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity(name = "addresses")
@Table(name = "addresses")
@Schema(hidden = true)
public class Address {

	@Id
	@Pattern(regexp = "^[0-9]{5}[0-9]{3}$", message = "Invalid CEP format.")
	@JsonProperty("cep")
	private String cep;

    @JsonProperty("logradouro")
	private String logradouro;

	@JsonProperty("complemento")
	private String complemento;

	@JsonProperty("bairro")
	private String bairro;

	@JsonProperty("localidade")
	private String localidade;

	@JsonProperty("uf")
	private String uf;

	@JsonProperty("ddd")
	private String ddd;

	@JsonProperty("users")
	@OneToMany(mappedBy = "address")
	@JsonIgnore
	private List<User> users;

	/**
	 * Get users 
	 *
	 * @return users
	 */

	public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

	/**
	 * Get cep
	 *
	 * @return cep
	 */
	@Schema(example = "01001000", description = "")
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		if(cep == null)
			return;
		this.cep = cep.replace("-", "");
	} 

	/**
	 * Get logradouro 
	 *
	 * @return logradouro  
	 */
	@Schema(example = "Praça da Sé", description = "")

	public String getLogradouro() {
		return logradouro;
		
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
		
	}

	/**
	 * Get complemento 
	 *
	 * @return complemento 
	 */

	@Schema(example = "lado ímpar", description = "")

	public String getComplemento() {
		return complemento;
		
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
		
	}

	/**
	 * Get bairro
	 *
	 * @return bairro 
	 */
	@Schema(example = "Sé", description = "")

	public String getBairro() {
		return bairro;
		
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
		
	}

	/**
	 * Get localidade 
	 *
	 * @return localidade 
	 */
	@Schema(example = "São Paulo", description = "")

	public String getLocalidade() {
		return localidade;
		
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
		
	}

	/**
	 * Get uf 
	 *
	 * @return uf 
	 */
	@Schema(example = "SP", description = "")

	public String getUf() {
		return uf;
		
	}

	public void setUf(String uf) {
		this.uf = uf;
		
	}

	/**
	 * Get ddd
	 *
	 * @return ddd
	 */
	@Schema(example = "11", description = "")

	public String getDdd() {
		return ddd;
		
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
		
}
