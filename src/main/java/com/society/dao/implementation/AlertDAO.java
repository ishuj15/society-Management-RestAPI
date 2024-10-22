package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.Alert;
import com.society.daoInterface.AlertInterface;
@Repository
public class AlertDAO extends GenericDAO<Alert> implements AlertInterface {

	@Override
	protected Alert mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Alert alert = new Alert();
		alert.setIdAlert(resultSet.getString("idAlert"));
		alert.setMessage(resultSet.getString("message"));
		alert.setDate(resultSet.getDate("date"));
		alert.setTargetRole(resultSet.getString("targetRole"));

		return alert;
	}

	public boolean addAlert(Alert alert) throws ClassNotFoundException, SQLException {
		String sqlQuery = String.format(
				"INSERT INTO alert (idAlert,  message, date,targetRole) VALUES ('%s','%s','%s','%s')",
				alert.getIdAlert(), alert.getMessage(), alert.getDate(), alert.getTargetRole());
		return executeQuery(sqlQuery);
	}
	public List<Alert> getAlertByRole(String role) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM alert WHERE targetRole = '" + role + "' OR targetRole = 'all'";

		return executeGetAllQuery(sqlQuery);
	}
	public Alert getAlertById(String alertId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM alert WHERE idAlert = \"" + alertId + "\"";

		return executeGetQuery(sqlQuery);
	}
 
	public List<Alert> getAllAlerts() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM alert";
		return executeGetAllQuery(sqlQuery);
	}

	public boolean deleteAlert(String alertId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM alert WHERE idAlert = \"" + alertId + "\"";
		return executeQuery(sqlQuery);
	}

	public boolean updateAlert(String userId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("UPDATE alert SET %s = '%s' WHERE idAlert = '%s'", columnToUpdate, newValue,
				userId);
		return executeQuery(sqlQuery);
	}
}
