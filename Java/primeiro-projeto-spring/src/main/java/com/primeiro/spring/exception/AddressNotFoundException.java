package com.primeiro.spring.exception;


public class AddressNotFoundException extends RuntimeException{

	public AddressNotFoundException() {
		super("Address not found");
	}
}
