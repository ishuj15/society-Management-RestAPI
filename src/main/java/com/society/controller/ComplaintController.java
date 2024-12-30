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
import com.society.Model.Complaint;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.ComplaintService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;
@RestController
public class ComplaintController {
	@Autowired
	private ComplaintService complaintService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 

	@PostMapping(path ="/complaint/{userId}")
	public ResponseEntity<Object> createComplaint(@Valid  @PathVariable String userId ,@RequestBody Complaint complaint ) throws ClassNotFoundException, SQLException {
        logger.info("Request to create complaint: {}", complaint);
        try {
			complaint.setIdComplaint(Helper.generateUniqueId());
			complaint.setUserId(userId);
			complaint.setStatus(str.pending);
			complaintService.createComplaint(complaint);
			logger.info("complaint created successfully: {}", complaint.getIdComplaint());

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLAINT_CREATED,  complaint);
		} catch (ClassNotFoundException e) {
			 logger.error("Unable to create complaint: {}", complaint);
			throw new ClassNotFoundException(str.unableToCreate);
		} catch (SQLException e) {
			 logger.error("Unable to create complaint: {}", complaint);
			throw new SQLException(str.unableToCreate);
		}

	} 
	
	@GetMapping(path ="/complaints")
	public ResponseEntity<Object> retriveAllComplaint()throws ClassNotFoundException, SQLException  {
			logger.info("Request to retrieve  all complaint : {}");

		try {
			List<Complaint> 	list = complaintService.retriveAllComplaint();
			 logger.info("complaints retrieved successfully: ");
			 return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new ClassNotFoundException(str.unableToRetrive);

		} catch (SQLException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new SQLException(str.unableToRetrive);
		}

	}
	
	@GetMapping(path ="/complaints/{userId}")
	public ResponseEntity<Object>retriveComplaintByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException  {
		logger.info("Request to retrieve  all complaint : {}");

		try {
			 List<Complaint>  list = complaintService.retriveComplaintByUser(userId);
			 logger.info("complaints retrieved successfully: ");
			 return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new SQLException(str.unableToRetrive);
		}
		 

	}
	
	@GetMapping(path ="/complaint/{complaintId}")
	public ResponseEntity<Object>retriveComplaintByComplaintId(@PathVariable String complaintId)throws ClassNotFoundException, SQLException  {
		 
			logger.info("Request to retrieve  all complaint : {}");

		try {
			 Complaint complaint = complaintService.getComplaintByComplaintId(complaintId);
			 logger.info("complaints retrieved successfully: ");
			 return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  complaint);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive all  complaint ");
			throw new SQLException(str.unableToRetrive);
		}
		 

	}
	@PatchMapping(path="/complaint/{complaintId}")
	public ResponseEntity<Object> updateComplaint(@PathVariable String complaintId, @RequestBody Complaint complaint)throws ClassNotFoundException, SQLException  {
		logger.info("Request to update complaint by ID: {}", complaintId);

		try {
			complaintService.updateComplaint(complaintId,complaint);
            logger.info("complaint updated successfully: {}", complaintId);
    		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLAINT_UPDATED,  null);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to update complaint ");
			throw new ClassNotFoundException(str.unableToUpdate);
		} catch (SQLException e) {
			logger.warn("Not able to update  complaint ");
			throw new SQLException(str.unableToUpdate);
		}
		

	}
	@DeleteMapping(path ="/complaint/{complaintId}")
	public ResponseEntity<Object> deleteComplaint(@PathVariable String complaintId) throws ClassNotFoundException, SQLException {
		logger.info("Request to delete complaint by ID: {}", complaintId);

		try {
			complaintService.deleteComplaint(complaintId);
            logger.info("complaint deleted successfully: {}", complaintId);
    		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLIANT_DELETED,  null);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to delete complaint ");
			throw new ClassNotFoundException(str.unableToDelete);

		} catch (SQLException e) {
			logger.warn("Not able to delete complaint ",complaintId);
			throw new SQLException(str.unableToDelete);
		}
		

	}
	
}