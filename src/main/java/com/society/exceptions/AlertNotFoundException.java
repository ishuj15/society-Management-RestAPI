package com.society.exceptions;

public class AlertNotFoundException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AlertNotFoundException(String message) {
		super(message);
	}
	
}
