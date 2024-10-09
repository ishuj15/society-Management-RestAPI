package com.society.controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.Visitor;
import com.society.service.VisitorService;
@RestController
public class VisitorController {
	private VisitorService visitorService;
	@PostMapping(path="/visitor")
	public void createVisitor(@RequestBody Visitor visitor) throws ClassNotFoundException, SQLException {
		visitorService.createVisitor(visitor);
	}
	@GetMapping(path="/visitors")
	public void retriveAllVisitors() {
		
	}
	@GetMapping(path="/visitors/{userId}")
	public void retriveVisitorByUser() {
		
	}
	@PatchMapping(path="/visitor/{id}")
	public void updateVisitor() {
		
	}
	@DeleteMapping(path="/visitor/{id}")
	public void deleteVisitorById() {
		
	}
	@GetMapping(path="/visitors/{userId}/pending")
	public void getAllPendingRequests() {
		
	}
//	@PatchMapping(path="/visitors/{id}")
//	public void verifyVisitor() {
//		
//	}
//	
	//pending requests 	
	//verify visitor

}
