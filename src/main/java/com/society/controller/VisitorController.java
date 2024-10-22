package com.society.controller;

import java.sql.SQLException;
import java.util.List;

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
@RestController
public class VisitorController {
	@Autowired 
	private VisitorService visitorService;
	
	@PostMapping(path="/visitor")
	public  ResponseEntity<Object> createVisitor(@RequestBody Visitor visitor) throws ClassNotFoundException, SQLException {
		visitor.setIdVisitor(Helper.generateUniqueId());
		visitorService.createVisitor(visitor);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_CREATED,  null);

	}
	@GetMapping(path="/visitors")
	public  ResponseEntity<Object> retriveAllVisitors() throws ClassNotFoundException, SQLException {
		List<Visitor> list= visitorService.retriveAllVisitors();
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	@GetMapping(path="/visitors/{userId}")
	public  ResponseEntity<Object> retriveVisitorByUser(@PathVariable String userId)  throws ClassNotFoundException, SQLException{
		List<Visitor> list= visitorService.retriveVisitorByUser(userId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	@PatchMapping(path="/visitor/{visitorId}")
	public  ResponseEntity<Object> updateVisitor()  throws ClassNotFoundException, SQLException{
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_UPDATED,  null);

	}
	@DeleteMapping(path="/visitor/{visitorId}")
	public  ResponseEntity<Object> deleteVisitorById(@PathVariable String visitorId)  throws ClassNotFoundException, SQLException{
		visitorService.deleteVisitorById(visitorId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.VISITOR_DELETED,  null);

	}
	@GetMapping(path="/visitors/{userId}/")
	public   ResponseEntity<Object> getAllPendingRequests(@PathVariable String userId, @RequestParam String status)  throws ClassNotFoundException, SQLException {
		List<Visitor> list= visitorService.getAllPendingRequests(userId,status);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
//	@PatchMapping(path="/visitors/{id}")
//	public void verifyVisitor() {
//		
//	}
//	
	//pending requests 	
	//verify visitor

}
