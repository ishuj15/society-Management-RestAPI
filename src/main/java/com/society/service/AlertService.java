package com.society.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.Alert;
import com.society.dao.implementation.AlertDAO;

@Service
public class AlertService {
	public AlertDAO alertDao=new AlertDAO();
	public void createAlert(Alert alert) throws ClassNotFoundException, SQLException {
		alertDao.addAlert(alert);
	}
	
	public List<Alert> retriveAllAlerts() throws ClassNotFoundException, SQLException {
		return alertDao.getAllAlerts();
		
	}
	public List<Alert> retriveAlertByRole(String role) throws ClassNotFoundException, SQLException {
		return alertDao.getAlertByRole(role);	
	}
	
	public List<String> updateAlert(Alert alert) throws ClassNotFoundException, SQLException {
		List<String> list= new ArrayList<>();
		if(alert.getTargetRole()!=null)
		{
			alertDao.updateAlert(alert.getIdAlert(), "targetRole", alert.getTargetRole());
			list.add("Target role updated successfully");
			
		}
		if(alert.getMessage()!=null)
		{
			alertDao.updateAlert(alert.getIdAlert(), "message", alert.getMessage());
			list.add("Message updated successfully");	
		}
		else
		{
			list.add("Not able to update");
			return list;
		}
		return list;
		
		
	}
	public void deleteAlert(String alertId) throws ClassNotFoundException, SQLException {
		alertDao.deleteAlert(alertId);
		
	}

}
