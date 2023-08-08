package com.cognixia.jump.exception;

public class UserExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	
    public UserExistsException(String resource) {
		super(resource + " already exists");
	}

}
