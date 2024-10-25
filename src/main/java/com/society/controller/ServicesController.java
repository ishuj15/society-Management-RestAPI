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
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.Services;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.ServicesService;
import com.society.util.Helper;

import jakarta.validation.Valid;
@RestController
public class ServicesController {
	@Autowired
	private ServicesService servicesService;
	
	@PostMapping(path="/service")
	public ResponseEntity<Object> createService(@Valid @RequestBody  Services service) throws ClassNotFoundException, SQLException {
		service.setIdServices(Helper.generateUniqueId());
		servicesService.createService(service);
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_CREATED,  null);

		
	} 
	@GetMapping(path="/services")
	public ResponseEntity<Object> retriveAllServices() throws ClassNotFoundException, SQLException{
		 List<Services> list =  servicesService.retriveAllServices();
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	@GetMapping(path="/services/{userId}")
	public ResponseEntity<Object> retriveServiceByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException {
		 List<Services> list = servicesService.retriveServiceByUser(userId);
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	@PatchMapping(path="/service/{serviceId}")
	public ResponseEntity<Object> updateService(@PathVariable String serviceId)throws ClassNotFoundException, SQLException {
		//will be implement later
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_UPDATED,   null);

	}
	@DeleteMapping(path="/service/{serviceId}")
	public ResponseEntity<Object> deleteServiceById(@PathVariable String serviceId) throws ClassNotFoundException, SQLException{
		servicesService.deleteServiceById(serviceId);
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_DELETED,   null);

	}
}
