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

import com.society.Model.Services;
import com.society.service.ServicesService;
@RestController
public class ServicesController {
	private ServicesService servicesService;
	@PostMapping(path="/service")
	public void createService(@RequestBody  Services service) throws ClassNotFoundException, SQLException {
		servicesService.createService(service);
		
	}
	@GetMapping(path="/services")
	public List<Services> retriveAllServices() throws ClassNotFoundException, SQLException{
		return servicesService.retriveAllServices();
	}
	@GetMapping(path="/services/{userId}")
	public List<Services> retriveServiceByUser(@PathVariable String userId)throws ClassNotFoundException, SQLException {
		return servicesService.retriveServiceByUser(userId);
	}
	@PatchMapping(path="/service/{id}")
	public List<String> updateService(@PathVariable String id)throws ClassNotFoundException, SQLException {
		//will be implement later
		return null;
	}
	@DeleteMapping(path="/service/{serviceId}")
	public void deleteServiceById(@PathVariable String serviceId) throws ClassNotFoundException, SQLException{
		servicesService.deleteServiceById(serviceId);
	}
}
