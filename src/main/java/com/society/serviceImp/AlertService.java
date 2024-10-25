package com.society.serviceImp;
 
import java.sql.SQLException;
import java.util.List;

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
public class AlertService implements AlertServiceInterface{
	@Autowired
	private AlertDAO alertDao;
	
	public AlertService(AlertDAO alertDao) {
		this.alertDao = alertDao;
	} 

	@Override
	public boolean addAlert(Alert alert) throws AlertsException, ClassNotFoundException, SQLException  {
		if(!alertDao.addAlert(alert))
		{
			throw new InternalServerException(ApiMessages.UNABLE_TO_CREATE_ALERT);
		}
		return true;
	}
	@Override
	public List<Alert> retriveAllAlerts() throws ClassNotFoundException, SQLException {
		List<Alert> list= alertDao.getAllAlerts();
		if(list.isEmpty())
			throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
		else
			return list;
	}
	@Override
	public List<Alert> retriveAlertByRole(String role) throws ClassNotFoundException, SQLException {
		List<Alert> list= alertDao.getAlertByRole(role);	
		
		if(list.isEmpty() || !Helper.isValidTarget(role))
			throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
		else
			return list;
	}
	@Override
	public boolean updateAlert(String alertId ,Alert alert) throws AlertsException,ClassNotFoundException, SQLException {
		Alert existingAlert=alertDao.getAlertById(alertId);
		if(existingAlert==null)
			throw new AlertsException(ApiMessages.UNABLE_TO_UPDATE_ALERT);
		else
		{
			
			if( alert.getTargetRole()!=null )
			{
				alertDao.updateAlert(alertId, ApiMessages.TARGET_ROLE, alert.getTargetRole());
			}
			if( alert.getMessage()!=null)
			{
				alertDao.updateAlert(alertId, ApiMessages.MESSAGE, alert.getMessage());	
			}	
			return true;

		}
	}
	@Override 
	public boolean deleteAlert(String alertId) throws AlertsException, ClassNotFoundException, SQLException {
		Alert alert=alertDao.getAlertById(alertId);
		if(alert==null) {
			throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
		}
		if(!alertDao.deleteAlert(alertId)) {
			throw new AlertsException(ApiMessages.UNABLE_TO_DELETE_ALERT);
		}
		return true;

	}
	@Override
	public Alert getAlertById(String alertId) throws ClassNotFoundException, SQLException {
		Alert alert=alertDao.getAlertById(alertId);
		if(alert==null) {
			throw new AlertsException(ApiMessages.ALERT_NOT_FOUND);
		}
		return alert;
			 
	}
	
}
