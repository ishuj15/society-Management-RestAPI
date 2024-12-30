package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Alert;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.AlertDAO;
import com.society.exceptions.AlertsException;
import com.society.exceptions.InternalServerException;
import com.society.serviceInterface.AlertServiceInterface;
import com.society.util.Helper;

@Service
public class AlertService implements AlertServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    private AlertDAO alertDao;

    public AlertService(AlertDAO alertDao) {
        this.alertDao = alertDao;
    }

    @Override
    public boolean addAlert(Alert alert) throws AlertsException, ClassNotFoundException, SQLException {
        try {
            logger.info("Adding alert: {}", alert);
            if (!alertDao.addAlert(alert)) {
                logger.error("Failed to add alert: {}", alert);
                throw new InternalServerException(ApiMessages.UNABLE_TO_CREATE_ALERT);
            }
            logger.info("Alert added successfully: {}", alert);
            return true;
        } catch (Exception e) {
            logger.error("Error while adding alert: {}", alert, e);
            throw new AlertsException("Failed to add alert");
        }
    }

    @Override
    public List<Alert> retriveAllAlerts() throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all alerts");
            List<Alert> list = alertDao.getAllAlerts();
            if (list.isEmpty()) {
                logger.error("No alerts found");
                throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
            }
            logger.info("Retrieved {} alerts", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving all alerts", e);
            throw new AlertsException("Failed to retrieve alerts");
        }
    }

    @Override
    public List<Alert> retriveAlertByRole(String role) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving alerts by role: {}", role);
            List<Alert> list = alertDao.getAlertByRole(role);
            if (list.isEmpty() || !Helper.isValidTarget(role)) {
                logger.error("No alerts found for role: {}", role);
                throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
            }
            logger.info("Retrieved {} alerts for role: {}", list.size(), role);
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving alerts by role: {}", role, e);
            throw new AlertsException("Failed to retrieve alerts by role");
        }
    }

    @Override
    public boolean updateAlert(String alertId, Alert alert) throws AlertsException, ClassNotFoundException, SQLException {
        try {
            logger.info("Updating alert with ID: {}", alertId);
            Alert existingAlert = alertDao.getAlertById(alertId);
            if (existingAlert == null) {
                logger.error("No alert found with ID: {}", alertId);
                throw new AlertsException(ApiMessages.UNABLE_TO_UPDATE_ALERT);
            }

            if (alert.getTargetRole() != null) {
                alertDao.updateAlert(alertId, ApiMessages.TARGET_ROLE, alert.getTargetRole());
            }
            if (alert.getMessage() != null) {
                alertDao.updateAlert(alertId, ApiMessages.MESSAGE, alert.getMessage());
            }

            logger.info("Alert updated successfully with ID: {}", alertId);
            return true;
        } catch (Exception e) {
            logger.error("Error while updating alert with ID: {}", alertId, e);
            throw new AlertsException("Failed to update alert");
        }
    }

    @Override
    public boolean deleteAlert(String alertId) throws AlertsException, ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting alert with ID: {}", alertId);
            Alert alert = alertDao.getAlertById(alertId);
            if (alert == null) {
                logger.error("No alert found with ID: {}", alertId);
                throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
            }
            if (!alertDao.deleteAlert(alertId)) {
                logger.error("Failed to delete alert with ID: {}", alertId);
                throw new AlertsException(ApiMessages.UNABLE_TO_DELETE_ALERT);
            }
            logger.info("Alert deleted successfully with ID: {}", alertId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting alert with ID: {}", alertId, e);
            throw new AlertsException("Failed to delete alert");
        }
    }

    @Override
    public Alert getAlertById(String alertId) throws AlertsException, ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving alert by ID: {}", alertId);
            Alert alert = alertDao.getAlertById(alertId);
            if (alert == null) {
                logger.error("No alert found with ID: {}", alertId);
                throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
            }
            logger.info("Alert retrieved: {}", alert);
            return alert;
        } catch (Exception e) {
            logger.error("Error while retrieving alert by ID: {}", alertId, e);
            throw new AlertsException("Failed to retrieve alert by ID");
        }
    }
}
