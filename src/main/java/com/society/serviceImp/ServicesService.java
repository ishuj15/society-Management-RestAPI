package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Services;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ServicesDAO;
import com.society.exceptions.ServiceException;
import com.society.serviceInterface.ServicesServiceInterface;

@Service
public class ServicesService implements ServicesServiceInterface {
	@Autowired
	public ServicesDAO serviceDao;
	@Override
	public boolean createService(Services service) throws ClassNotFoundException, SQLException {
		if(!serviceDao.addService(service))
			throw new ServiceException(ApiMessages.UNABLE_TO_CREATE_SERVICE);
		return true;

	} 
	@Override
	public List<Services> retriveAllServices() throws ClassNotFoundException, SQLException{
		List<Services> list = serviceDao.getAllServices();
		if(list.isEmpty())
			throw new ServiceException(ApiMessages.SERVICE_NOT_FOUND);
		else 
			return list;

	}
	@Override
	public List<Services> retriveServiceByUser(String userId) throws ClassNotFoundException, SQLException{
		List<Services> list =  serviceDao.getServiceByUserId(userId);
		if(list.isEmpty())
			throw new ServiceException(ApiMessages.SERVICE_NOT_FOUND);
		else 
			return list;

	}
	@Override
	public boolean updateService()throws ClassNotFoundException, SQLException {
		//will be implement later
		return true;

	}
	@Override
	public boolean deleteServiceById(String serviceId)throws ClassNotFoundException, SQLException {
		if(!serviceDao.deleteService(serviceId))
			throw new ServiceException(ApiMessages.UNABLE_TO_DELETE_SERVICE);
		return true;

	}
}
