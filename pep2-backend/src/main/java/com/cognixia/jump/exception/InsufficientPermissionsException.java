package com.cognixia.jump.exception;

public class InsufficientPermissionsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InsufficientPermissionsException() {
		super("Invalid permissions");
	}

}
