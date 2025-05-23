package com.society.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.society.Model.ApiResponseStatus;

public class ApiResponseHandler {
	//Response entity for success
	public static ResponseEntity<Object> buildResponse(ApiResponseStatus status, HttpStatus statusCode, String message,
			Object data) {
		ApiResponse apiResponse = new ApiResponse(status, message, data); 
		return new ResponseEntity<>(apiResponse, statusCode);
	}
	//login
//	public static ResponseEntity<Object> buildResponse(ApiResponseStatus status, HttpStatus statusCode, String message,
//			Object token, Object data) {
//		ApiResponse apiResponse = new ApiResponse(status, message, token,data); 
//		return new ResponseEntity<>(apiResponse, statusCode);
//	}
	//Response entity for failure
	public static ResponseEntity<Object> buildResponse(ApiResponseStatus status, HttpStatus statusCode, String message,
			Object data, Object error) {
		ApiResponse apiResponse = new ApiResponse(status, message, data, error);
		return new ResponseEntity<>(apiResponse, statusCode);
	}
	
	public static ResponseEntity<Object> buildResponse(ApiResponseStatus status, HttpStatus statusCode, String message,
			Object data, Integer page, Integer size, Integer totalItems, Integer totalPages) {
		ApiResponse apiResponse = new ApiResponse(status, message, data, page, size, totalItems, totalPages);
		return new ResponseEntity<>(apiResponse, statusCode);
	}		
}