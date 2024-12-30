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

	@PostMapping(path ="/complaint/{userId}")
	public ResponseEntity<Object> createComplaint(@Valid  @PathVariable String userId ,@RequestBody Complaint complaint ) throws ClassNotFoundException, SQLException {
		complaint.setIdComplaint(Helper.generateUniqueId());
		complaint.setUserId(userId);
		complaint.setStatus(str.pending);
		complaintService.createComplaint(complaint);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLAINT_CREATED,  complaint);

	} 
	
	@GetMapping(path ="/complaints")
	public ResponseEntity<Object> retriveAllComplaint()throws ClassNotFoundException, SQLException  {
		 List<Complaint>  list= complaintService.retriveAllComplaint();
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	
	@GetMapping(path ="/complaints/{userId}")
	public ResponseEntity<Object>retriveComplaintByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException  {
		 List<Complaint>  list = complaintService.retriveComplaintByUser(userId);
		 
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	}
	
	@GetMapping(path ="/complaint/{complaintId}")
	public ResponseEntity<Object>retriveComplaintByComplaintId(@PathVariable String complaintId)throws ClassNotFoundException, SQLException  {
		 Complaint complaint = complaintService.getComplaintByComplaintId(complaintId);
		 
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  complaint);

	}
	@PatchMapping(path="/complaint/{complaintId}")
	public ResponseEntity<Object> updateComplaint(@PathVariable String complaintId, @RequestBody Complaint complaint)throws ClassNotFoundException, SQLException  {
		complaintService.updateComplaint(complaintId,complaint);
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLAINT_UPDATED,  null);

	}
	@DeleteMapping(path ="/complaint/{complaintId}")
	public ResponseEntity<Object> deleteComplaint(@PathVariable String complaintId) throws ClassNotFoundException, SQLException {
		complaintService.deleteComplaint(complaintId);
		
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.COMPLIANT_DELETED,  null);

	}
	
}