package com.primeiro.spring.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController{

	@GetMapping("/")
	public String getIndex(String nome) {
		return "Bem vindo ao meu primeiro projeto Spring! " + nome;
	}
}

