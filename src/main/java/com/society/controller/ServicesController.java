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
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.Services;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.exceptions.ServiceException;
import com.society.serviceImp.ServicesService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;
@RestController
public class ServicesController {
	@Autowired
	private ServicesService servicesService;
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 

	
	@PostMapping(path="/service/{userId}")
	public ResponseEntity<Object> createService(@Valid  @PathVariable String userId ,@RequestBody  Services service) throws ClassNotFoundException, SQLException {
		service.setIdServices(Helper.generateUniqueId());
		service.setUserId(userId);
		
		logger.info("Request to create service: {}", service);

		if(servicesService.createService(service))
		{
			logger.info("Service created successfully: {}", service.getIdServices());
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_CREATED, service);
		}
		else
		{
            logger.error("Unable to create service: {}", service);
            throw new ServiceException(ApiMessages.UNABLE_TO_CREATE_SERVICE);
		}
		

		
	} 
	@GetMapping(path="/services")
	public ResponseEntity<Object> retriveAllServices() throws ClassNotFoundException, SQLException{
		logger.info("Request to retrieve  all services : {}");

		try {
			List<Services> list =  servicesService.retriveAllServices();
			 logger.info("services retrieved successfully: ");

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  services ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  services ");
			throw new SQLException(str.unableToRetrive);
		}

	}
	@GetMapping(path="/services/{userId}")
	public ResponseEntity<Object> retriveServiceByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException {
		 
		logger.info("Request to retrieve  all services : {}");
		try {
			List<Services> list = servicesService.retriveServiceByUser(userId);
			logger.info("services retrieved successfully: ");
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  services ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  services ");
			throw new SQLException(str.unableToRetrive);
		}

	}
	@PatchMapping(path="/service/{serviceId}")
	public ResponseEntity<Object> updateService(@PathVariable String serviceId)throws ClassNotFoundException, SQLException {
		//will be implement later
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_UPDATED,   null);

	}
	@DeleteMapping(path="/service/{serviceId}")
	public ResponseEntity<Object> deleteServiceById(@PathVariable String serviceId) throws ClassNotFoundException, SQLException{
		logger.info("Request to delete service by ID: {}", serviceId);

		try {
			servicesService.deleteServiceById(serviceId);
            logger.info("service deleted successfully: {}", serviceId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.SERVICE_DELETED,   null);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to delete service ");
			throw new ClassNotFoundException(str.unableToDelete);
		} catch (SQLException e) {
			logger.warn("Not able to delete service ",serviceId);
			throw new SQLException(str.unableToDelete);
		}

	}
}