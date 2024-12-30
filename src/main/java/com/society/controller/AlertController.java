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

import com.society.Model.Alert;
import com.society.Model.ApiResponseStatus;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.exceptions.AlertsException;
import com.society.serviceImp.AlertService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;
@RestController
public class AlertController {
	@Autowired
	private AlertService alertService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 


	@PostMapping(path="/alert")
	public ResponseEntity<Object> createAlert( @Valid @RequestBody  Alert alert) throws Exception {
	
		alert.setIdAlert(Helper.generateUniqueId());
        logger.info("Request to create alert: {}", alert);

			try {
				alertService.addAlert(alert);
				logger.info("alert created successfully: {}", alert.getIdAlert());

				return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.CREATED, ApiMessages.ALERT_CREATED,alert);
			} catch (AlertsException e) {
				 logger.error("Unable to create alert: {}", alert);
				throw new AlertsException(str.unableToCreate);
			} catch (Exception e) {
				 logger.error("Unable to create alert: {}", alert);
				throw new Exception(str.unableToCreate);
			} 
		}
	 
	@GetMapping(path="/alerts")
	public ResponseEntity<Object> retriveAllAlert() throws ClassNotFoundException, SQLException {
		logger.info("Request to retrieve  all alert : {}");
		try {
			List<Alert>list = alertService.retriveAllAlerts();
			 logger.info("alerts retrieved successfully: ");
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  alert ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  alert ");
			throw new SQLException(str.unableToRetrive);
		}
	}
	
	@GetMapping(path="/alerts/{role}")
	public ResponseEntity<Object> retriveAlertsByRole(@PathVariable String role) throws ClassNotFoundException, SQLException {
		logger.info("Request to retrieve alert by role: {}");
		try {
			List<Alert> list = alertService.retriveAlertByRole(role);
            logger.info("alerts retrieved successfully: ");

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive alert by role");

			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive alert by role");
			throw new SQLException(str.unableToRetrive);
		}

	}
	@GetMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> retriveAlertById(@PathVariable String alertId) throws ClassNotFoundException, SQLException {
	
		logger.info("Request to retrieve alert by ID: {}", alertId);
		try {
			Alert	alert = alertService.getAlertById(alertId);
            logger.info("alerts retrieved successfully: {}", alertId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  alert);
		} catch (AlertsException e) {
		logger.warn("Not able to retrive alert by Id",alertId);
			throw new AlertsException(str.unableToRetrive);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive alert by Id",alertId);
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive alert by Id",alertId);
			throw new SQLException(str.unableToRetrive);
		}

	}
	@PatchMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> updateAlert(@PathVariable String alertId,@RequestBody Alert alert) throws ClassNotFoundException, SQLException {
		logger.info("Request to update alert by ID: {}", alertId);

		 try {
				alertService.updateAlert(alertId,alert);
	            logger.info("alerts updated successfully: {}", alertId);
				return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.ALERT_UPDATED,null);
			} catch (AlertsException e ) {
				logger.warn("Not able to update alert ",alertId);
				throw new AlertsException(str.unableToUpdate);
			} 
	}

	@DeleteMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> deleteAlert(@PathVariable String alertId) throws Exception {
		logger.info("Request to delete alert by ID: {}", alertId);

		try {
			alertService.deleteAlert(alertId);
            logger.info("alerts deleted successfully: {}", alertId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.ALERT_DELETED,null);
		} catch (AlertsException e) {
			logger.warn("Not able to delete alert ",alertId);
			throw new AlertsException(str.unableToDelete);
		} catch (Exception e) {
			logger.warn("Not able to delete alert ",alertId);
			throw new Exception(str.unableToDelete);
			} 

	}
}