package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Alert;

public interface AlertServiceInterface {
	public boolean addAlert(Alert alert) throws SQLException, ClassNotFoundException ;
	public List<Alert> retriveAlertByRole(String role) throws SQLException, ClassNotFoundException ;
	public List<Alert> retriveAllAlerts() throws SQLException, ClassNotFoundException ;
	public boolean updateAlert(String alertId,Alert alert) 
			throws SQLException, ClassNotFoundException;
	public boolean deleteAlert(String alertId) throws SQLException, ClassNotFoundException ;
	public Alert getAlertById(String alertId) throws SQLException, ClassNotFoundException ;

}
