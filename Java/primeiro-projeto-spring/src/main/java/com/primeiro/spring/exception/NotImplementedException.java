package com.primeiro.spring.exception;


public class NotImplementedException extends RuntimeException {

	public NotImplementedException() {
		super("Route not implemented");
	}
}
