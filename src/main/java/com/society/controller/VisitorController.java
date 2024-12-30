package com.society.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.Visitor;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.VisitorService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;
@RestController
public class VisitorController {
	@Autowired 
	private VisitorService visitorService;
	//Logging 
    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class); 

	
	@PostMapping(path="/visitor/{userId}")
	public  ResponseEntity<Object> createVisitor(@Valid  @PathVariable String userId ,@RequestBody Visitor visitor) throws ClassNotFoundException, SQLException {
        logger.info("Request to create visitor: {}", visitor);

		try {
			visitor.setIdVisitor(Helper.generateUniqueId());
			visitor.setUserId(userId);
			visitorService.createVisitor(visitor);
			logger.info("visitor created successfully: {}", visitor.getIdVisitor());

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_CREATED,  visitor);
		} catch (ClassNotFoundException e) {
			 logger.error("Unable to create visitor: {}", visitor);
				throw new ClassNotFoundException(str.unableToCreate);
		} catch (SQLException e) {
			 logger.error("Unable to create visitor: {}", visitor);
				throw new SQLException(str.unableToCreate);
		}

	}
	@GetMapping(path="/visitors")
	public  ResponseEntity<Object> retriveAllVisitors() throws ClassNotFoundException, SQLException {
		logger.info("Request to retrieve  all visitors : {}");

		try {
			List<Visitor> list= visitorService.retriveAllVisitors();
			 logger.info("visitors retrieved successfully: ");

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  visitors ");
			throw new ClassNotFoundException(str.unableToRetrive);

		} catch (SQLException e) {
			logger.warn("Not able to retrive all  visitors ");
			throw new SQLException(str.unableToRetrive);
		}

	}
	@GetMapping(path="/visitors/{userId}")
	public  ResponseEntity<Object> retriveVisitorByUser(@PathVariable String userId)  throws ClassNotFoundException, SQLException{
		logger.info("Request to retrieve  all visitors : {}");

		try {
			List<Visitor> list= visitorService.retriveVisitorByUser(userId);
			 logger.info("visitors retrieved successfully: ");

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  visitors ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  visitors ");
			throw new SQLException(str.unableToRetrive);
		}

	}
	@PatchMapping(path="/visitor/{visitorId}")
	public  ResponseEntity<Object> updateVisitor(@RequestBody Visitor visitor)  throws ClassNotFoundException, SQLException{
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_UPDATED,  null);

	}
	@DeleteMapping(path="/visitor/{visitorId}")
	public  ResponseEntity<Object> deleteVisitorById(@PathVariable String visitorId)  throws ClassNotFoundException, SQLException{
		logger.info("Request to delete visitor by ID: {}", visitorId);

		try {
			visitorService.deleteVisitorById(visitorId);
            logger.info("visitor deleted successfully: {}", visitorId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_DELETED,  null);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to delete visitor ");
			throw new ClassNotFoundException(str.unableToDelete);
		} catch (SQLException e) {
			logger.warn("Not able to delete visitor ",visitorId);
			throw new SQLException(str.unableToDelete);
		}
	}
	@GetMapping(path="/visitors/{userId}/status")
	public   ResponseEntity<Object> getAllPendingRequests(@PathVariable String userId, @RequestParam String status)  throws ClassNotFoundException, SQLException {
		logger.info("Request to retrive visitor by userID: {}", userId);
		try {
			List<Visitor> list= visitorService.getAllPendingRequests(userId,status);
            logger.info("visitor retrive successfully: {}", userId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive visitor ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive visitor ",userId);
			throw new SQLException(str.unableToRetrive);
		}

	}
//	@PatchMapping(path="/visitors/{id}")
//	public void verifyVisitor() {
//		
//	}
//	
	//pending requests 	
	//verify visitor

}