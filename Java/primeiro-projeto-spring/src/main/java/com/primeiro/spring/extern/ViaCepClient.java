package com.primeiro.spring.extern;

import com.primeiro.spring.model.Address;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
	@RequestMapping(method = RequestMethod.GET, value = "/{cep}/json", consumes = "application/json")
	Address getCep(@PathVariable("cep") String cep);
}
