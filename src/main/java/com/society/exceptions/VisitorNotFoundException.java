package com.society.exceptions;

public class VisitorNotFoundException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public VisitorNotFoundException(String message) {
		super(message);
	}
	
}
