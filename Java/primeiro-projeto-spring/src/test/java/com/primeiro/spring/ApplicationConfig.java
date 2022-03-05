package com.primeiro.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.primeiro.spring.extern.ViaCepClient;
import com.primeiro.spring.mock.ViaCepMock;

@Configuration
@EnableAutoConfiguration
@Import({ FeignClientsConfiguration.class, FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
@ComponentScan(basePackages = "com.primeiro.spring", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Application.class) })
public class ApplicationConfig {

	@Lazy
	@Autowired
	private WireMockServer wireMockServer;

	@Lazy
	@Autowired
	private ViaCepMock viaCepMock;

	@Autowired
	public ApplicationConfig(EntityManager entityManager) {
		Fixtures.setEntityManager(entityManager);
	}

	@Bean
	public ObjectMapper createMapper() {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	@Bean
	public ViaCepMock createViaCepMock() {
		return new ViaCepMock();
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public WireMockServer mockViaCep() throws Exception {
		WireMockServer wireMock = new WireMockServer(WireMockConfiguration.options().dynamicPort());
		viaCepMock.setupMockViaCepResponse(wireMock);
		return wireMock;
	}

	@Bean
	@Autowired
	public ViaCepClient createViaCepClient(Encoder encoder, Decoder decoder, Contract contract) {
		return Feign.builder().encoder(encoder).decoder(decoder).contract(contract).target(ViaCepClient.class,
				"http://localhost:" + wireMockServer.port());
	}
}
