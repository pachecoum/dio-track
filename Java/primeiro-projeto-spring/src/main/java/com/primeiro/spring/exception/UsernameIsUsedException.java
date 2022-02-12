package com.primeiro.spring.exception;


public class UsernameIsUsedException extends RuntimeException {

	public UsernameIsUsedException() {
		super("the username is already being used.");
	}
}

