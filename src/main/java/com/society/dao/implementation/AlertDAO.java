package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.society.Model.Alert;
import com.society.daoInterface.AlertInterface;

@Repository
public class AlertDAO extends GenericDAO<Alert> implements AlertInterface {

    private static final Logger logger = LoggerFactory.getLogger(AlertDAO.class);

    @Override
    protected Alert mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Alert alert = new Alert();
        alert.setIdAlert(resultSet.getString("idAlert"));
        alert.setMessage(resultSet.getString("message"));
        alert.setDate(resultSet.getDate("date"));
        alert.setTargetRole(resultSet.getString("targetRole"));
        return alert;
    }

    public boolean addAlert(Alert alert) {
        String sqlQuery = String.format(
                "INSERT INTO alert (idAlert, message, date, targetRole) VALUES ('%s', '%s', '%s', '%s')",
                alert.getIdAlert(), alert.getMessage(), alert.getDate(), alert.getTargetRole());

        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error adding alert: {}", e.getMessage(), e);
            return false;
        }
    }

    public List<Alert> getAlertByRole(String role) {
        String sqlQuery = "SELECT * FROM alert WHERE targetRole = '" + role + "' OR targetRole = 'all'";
        try {
            return executeGetAllQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching alerts for role {}: {}", role, e.getMessage(), e);
            return null;
        }
    }

    public Alert getAlertById(String alertId) {
        String sqlQuery = "SELECT * FROM alert WHERE idAlert = \"" + alertId + "\"";
        try {
            return executeGetQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching alert by ID {}: {}", alertId, e.getMessage(), e);
            return null;
        }
    }

    public List<Alert> getAllAlerts() {
        String sqlQuery = "SELECT * FROM alert";
        try {
            return executeGetAllQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching all alerts: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean deleteAlert(String alertId) {
        String sqlQuery = "DELETE FROM alert WHERE idAlert = \"" + alertId + "\"";
        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error deleting alert by ID {}: {}", alertId, e.getMessage(), e);
            return false;
        }
    }

    public boolean updateAlert(String userId, String columnToUpdate, String newValue) {
        String sqlQuery = String.format("UPDATE alert SET %s = '%s' WHERE idAlert = '%s'", columnToUpdate, newValue, userId);
        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error updating alert by ID {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}
