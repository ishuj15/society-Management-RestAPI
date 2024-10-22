package com.society.exceptions;

public class ComplaintNotFoundException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ComplaintNotFoundException(String message) {
		super(message);
	}
	
}
