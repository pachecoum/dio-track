package com.primeiro.spring.dto;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.modelmapper.ModelMapper;

import io.swagger.v3.oas.annotations.media.Schema;

import com.primeiro.spring.model.Address;

@Schema(name = "SimpleAddress")
public class AddressDTO {

	static protected ModelMapper modelMapper = new ModelMapper();

	@Pattern(regexp = "^[0-9]{5}[0-9]{3}$", message = "Invalid CEP format.")
	@JsonProperty("cep")
	@NotNull(message = "Cep can not been null")
	private String cep;

    @JsonProperty("logradouro")
	//@NotNull(message = "The field can't be null")
	//@NotBlank(message = "The field can't be blank")
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

	public static AddressDTO fromEntity(Address address) {
		AddressDTO addressDto = modelMapper.map(address, AddressDTO.class);

		return addressDto;
	}

	public static Address ToEntity(AddressDTO addressDto) {

		Address address = modelMapper.map(addressDto, Address.class);

		return address;
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
		this.cep = cep;
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
