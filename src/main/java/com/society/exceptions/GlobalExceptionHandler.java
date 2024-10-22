package com.society.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.society.Model.ApiResponseStatus;
import com.society.constants.ApiMessages;
import com.society.dto.ApiError;
import com.society.dto.ApiResponseHandler;
@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler{
	//ALERTS
	@ExceptionHandler(AlertNotFoundException.class)
	public ResponseEntity<Object> handleAlertNotFound(AlertNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.ALERT_NOT_FOUND, null,apiError);
	}
	
	//COMPLAINTS
	@ExceptionHandler(ComplaintNotFoundException.class)
	public ResponseEntity<Object> handleComplaintNotFound(ComplaintNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.COMPLAINT_NOT_FOUND, null,apiError);
	}
	
	//NOTICES
	@ExceptionHandler(NoticeNotFoundException.class)
	public ResponseEntity<Object> handleNoticeNotFound(NoticeNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.NOTICE_NOT_FOUND, null,apiError);
	}

	//USERS
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.USER_NOT_FOUND, null,apiError);
	}

	//SERVICES
	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<Object> handleServiceNotFound(ServiceNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.SERVICE_NOT_FOUND, null,apiError);
	}

	//VISITOR
	@ExceptionHandler(VisitorNotFoundException.class)
	public ResponseEntity<Object> handleVisitorNotFound(VisitorNotFoundException ex) {
		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.VISITOR_NOT_FOUND, null,apiError);
	}


}
