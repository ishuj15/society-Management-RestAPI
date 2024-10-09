package com.society.daoInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Alert;

public interface AlertInterface {
	public boolean addAlert(Alert alert)throws ClassNotFoundException, SQLException ;
	public List<Alert> getAlertByRole(String role) throws SQLException, ClassNotFoundException ;
	public List<Alert> getAllAlerts() throws SQLException, ClassNotFoundException;
	public boolean deleteAlert(String alertId) throws SQLException, ClassNotFoundException ;
	public boolean updateAlert(String userId, String columnToUpdate, String newValue) throws SQLException, ClassNotFoundException;

}
