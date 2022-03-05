package com.primeiro.spring.dto;

import java.util.Objects;

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

	//@NotNull(message = "The field can't be null")
	//@NotBlank(message = "The field can't be blank")
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

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (cep == null? 0 : cep.hashCode());
		hash = 31 * hash + (logradouro == null? 0 : logradouro.hashCode());
		hash = 31 * hash + (complemento == null? 0 : complemento.hashCode());
		hash = 31 * hash + (bairro == null? 0 : bairro.hashCode());
		hash = 31 * hash + (localidade == null? 0 : localidade.hashCode());
		hash = 31 * hash + (uf == null? 0 : uf.hashCode());
		hash = 31 * hash + (ddd == null? 0 : ddd.hashCode());

		// or
		hash *= Objects.hash(cep, logradouro, complemento, bairro, localidade, uf, ddd);

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (o instanceof AddressDTO) return false;

		AddressDTO address = (AddressDTO)o;
		return cep.equals(address.getCep()) && logradouro.equals(address.getLogradouro()) 
			&& complemento.equals(address.getComplemento()) && bairro.equals(address.getBairro())
			&& localidade.equals(address.getLocalidade()) && uf.equals(address.getUf())
			&& ddd.equals(address.getDdd());
	}

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
