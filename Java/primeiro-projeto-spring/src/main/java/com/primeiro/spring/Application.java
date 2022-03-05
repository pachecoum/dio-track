package com.primeiro.spring;

import static org.springdoc.core.Constants.ALL_PATTERN;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springdoc.core.GroupedOpenApi;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.primeiro.spring.extern"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	public GroupedOpenApi firstApi() {
		return GroupedOpenApi
			.builder()
			.group("API v1")
			.pathsToMatch("/" + ALL_PATTERN)
			.addOpenApiCustomiser(openApi -> openApi
					.info(
						new Info()
						.title("First spring API")
						.description("First REST spring.")
						.version("v1.0.0")
						.license(new License()
							.name("Apache 2.0")
							.url("")
							)
						.contact(new Contact()
							.name("Marcos Pacheco")
							.url("https://github.com/pachecoum")
							.email("marcospacheco10111@gmail.com"))
						))
			.build();
	}

}
