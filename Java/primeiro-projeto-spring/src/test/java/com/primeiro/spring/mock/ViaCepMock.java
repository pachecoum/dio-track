package com.primeiro.spring.mock;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.primeiro.spring.DataForTest;
import com.primeiro.spring.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Lazy
public class ViaCepMock {

	@Autowired
	private ObjectMapper mapper;

	private List<Address> addresses = DataForTest.addresses;

	public void setupMockViaCepResponse(WireMockServer mockService) throws Exception {

		for (Address address : addresses)
			mockService.stubFor(WireMock.get(WireMock.urlPathMatching("/" + address.getCep() + "/json"))
					.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value())
							.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
							.withBody(mapper.writeValueAsString(address))));
	}
}
