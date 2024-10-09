package com.society.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.Complaint;
import com.society.service.ComplaintService;
@RestController
public class ComplaintController {
	private ComplaintService complaintService;
	@PostMapping(path ="/complaint")
	public void createComplaint(@RequestBody Complaint complaint) throws ClassNotFoundException, SQLException {
		complaintService.createComplaint(complaint);
	}
	@GetMapping(path ="/complaints")
	public List<Complaint> retriveAllComplaint()throws ClassNotFoundException, SQLException  {
		return complaintService.retriveAllComplaint();
	}
	@GetMapping(path ="/complaints/{userId}")
	public List<Complaint> retriveComplaintByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException  {
		 return complaintService.retriveComplaintByUser(userId);
	}
	@PatchMapping(path="/complaint/{id}")
	public List<String> updateComplaint( @RequestBody Complaint complaint)throws ClassNotFoundException, SQLException  {
		return complaintService.updateComplaint(complaint);
	}
	@DeleteMapping(path ="/complaint/{complaintId}")
	public void deleteComplaint(@PathVariable String complaintId) throws ClassNotFoundException, SQLException {
		complaintService.deleteComplaint(complaintId);
	}
	
}
