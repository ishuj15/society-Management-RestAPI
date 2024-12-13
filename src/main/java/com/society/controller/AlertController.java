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

import com.society.Model.Alert;
import com.society.Model.ApiResponseStatus;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.exceptions.AlertsException;
import com.society.serviceImp.AlertService;
import com.society.util.Helper;

import jakarta.validation.Valid;
@RestController
public class AlertController {
	@Autowired
	private AlertService alertService;
	

	@PostMapping(path="/alert")
	public ResponseEntity<Object> createAlert( @Valid @RequestBody  Alert alert) throws ClassNotFoundException, SQLException {
	
		alert.setIdAlert(Helper.generateUniqueId());
			try {
				alertService.addAlert(alert);
			} catch (AlertsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.CREATED, ApiMessages.ALERT_CREATED,alert);
		}
	 
	@GetMapping(path="/alerts")
	public ResponseEntity<Object> retriveAllAlert() throws ClassNotFoundException, SQLException {
		List<Alert> list= alertService.retriveAllAlerts();
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
	}
	
	@GetMapping(path="/alerts/{role}")
	public ResponseEntity<Object> retriveAlertsByRole(@PathVariable String role) throws ClassNotFoundException, SQLException {
		List<Alert> list= alertService.retriveAlertByRole(role);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	@GetMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> retriveAlertById(@PathVariable String alertId) throws ClassNotFoundException, SQLException {
		System.out.println("controller layer");

		Alert alert =alertService.getAlertById(alertId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  alert);

	}
	@PatchMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> updateAlert(@PathVariable String alertId,@RequestBody Alert alert) throws ClassNotFoundException, SQLException {
		 
		 try {
				alertService.updateAlert(alertId,alert);
			} catch (AlertsException e ) {
				e.printStackTrace();
			} 
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.ALERT_UPDATED,null);
	}

	@DeleteMapping(path="/alert/{alertId}")
	public ResponseEntity<Object> deleteAlert(@PathVariable String alertId) throws ClassNotFoundException, SQLException {
		
		try {
			
			alertService.deleteAlert(alertId);
		} catch (AlertsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.ALERT_DELETED,null);

	}
}
