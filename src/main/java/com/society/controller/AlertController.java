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

import com.society.Model.Alert;
import com.society.service.AlertService;
@RestController
public class AlertController {
	private AlertService alertService;
	public AlertController(AlertService alertService)
	{
		this.alertService=alertService;
	}
	@PostMapping(path="/alert")
	public void createAlert(@RequestBody  Alert alert) throws ClassNotFoundException, SQLException {
		alertService.createAlert(alert);
		}
	@GetMapping(path="/alerts")
	public List<Alert> retriveAllAlert() throws ClassNotFoundException, SQLException {
		return alertService.retriveAllAlerts();
		
	}
	@GetMapping(path="/alerts/{role}")
	public List<Alert> retriveUserByRole(@PathVariable String role) throws ClassNotFoundException, SQLException {
		return alertService.retriveAlertByRole(role);
		
	}
	@PatchMapping(path="/alert/{id}")
	public List<String> updateAlert(@RequestBody Alert alert) throws ClassNotFoundException, SQLException {
		return alertService.updateAlert(alert);
		
	}
	@DeleteMapping(path="/alert/{id}")
	public void deleteAlert(@PathVariable String id) throws ClassNotFoundException, SQLException {
		alertService.deleteAlert(id);
		
	}
}
