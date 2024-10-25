package com.society.exceptions;

import java.time.LocalDateTime;

import org.apache.coyote.BadRequestException;
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
	
	@ExceptionHandler({ AlertsException.class, ComplaintException.class, 
			NoticeException.class, UserException.class, 
			UserException.class,VisitorException.class})
	public ResponseEntity<Object> handleNotFoundExceptions(Exception ex) {
	    String errorMessage;
	    String apiMessage;

	    if (ex instanceof AlertsException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.ALERT_NOT_FOUND;
	    } else if (ex instanceof ComplaintException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.COMPLAINT_NOT_FOUND;
	    }
	    else if (ex instanceof NoticeException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.NOTICE_NOT_FOUND;
	       }
	    else if (ex instanceof ServiceException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.SERVICE_NOT_FOUND;
	    }
	    else if (ex instanceof UserException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.USER_NOT_FOUND;
	    }
	    else if (ex instanceof VisitorException) {
	        errorMessage = ex.getMessage();
	        apiMessage = ApiMessages.VISITOR_NOT_FOUND;
	    }
	    else {
	        errorMessage = "Unexpected error";
	        apiMessage = "Not Found";
	    }

	    ApiError apiError = new ApiError(LocalDateTime.now(), errorMessage, apiMessage);
	    return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, apiMessage, null, apiError);
	}

	 @ExceptionHandler({ BadRequestException.class })
	    public ResponseEntity<Object> handleBadRequestExceptions(BadRequestException ex) {
	        String errorMessage = ex.getMessage();
	        String apiMessage = ApiMessages.BAD_REQUEST_MESSAGE;

	        ApiError apiError = new ApiError(LocalDateTime.now(), errorMessage, apiMessage);
	        return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.BAD_REQUEST, apiMessage, null, apiError);
	    }

	    // Handle 500 - Internal Server Error exceptions
	    @ExceptionHandler({ Exception.class }) // General Exception to catch unexpected errors
	    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
	        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "An internal error occurred";
	        String apiMessage = ApiMessages.INTERNAL_SERVER_ERROR;

	        ApiError apiError = new ApiError(LocalDateTime.now(), errorMessage, apiMessage);
	        return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.INTERNAL_SERVER_ERROR, apiMessage, null, apiError);
	    }
	
	
	
//	//ALERTS
//	@ExceptionHandler(AlertsException.class)
//	public ResponseEntity<Object> handleAlertNotFound(AlertsException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.ALERT_NOT_FOUND, null,apiError);
//	}
//	 
//	//COMPLAINTS
//	@ExceptionHandler(ComplaintNotFoundException.class)
//	public ResponseEntity<Object> handleComplaintNotFound(ComplaintNotFoundException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.COMPLAINT_NOT_FOUND, null,apiError);
//	}
//	
//	//NOTICES
//	@ExceptionHandler(NoticeNotFoundException.class)
//	public ResponseEntity<Object> handleNoticeNotFound(NoticeNotFoundException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.NOTICE_NOT_FOUND, null,apiError);
//	}
//
//	//USERS
//	@ExceptionHandler(UserNotFoundException.class)
//	public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.USER_NOT_FOUND, null,apiError);
//	}
//
//	//SERVICES
//	@ExceptionHandler(ServiceNotFoundException.class)
//	public ResponseEntity<Object> handleServiceNotFound(ServiceNotFoundException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.SERVICE_NOT_FOUND, null,apiError);
//	}
//
//	//VISITOR
//	@ExceptionHandler(VisitorNotFoundException.class)
//	public ResponseEntity<Object> handleVisitorNotFound(VisitorNotFoundException ex) {
//		ApiError apiError= new ApiError(LocalDateTime.now(), ex.getMessage(), ApiMessages.ALERT_NOT_FOUND);
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.VISITOR_NOT_FOUND, null,apiError);
//	}
//

}
