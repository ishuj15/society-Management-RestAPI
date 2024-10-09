package com.society.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.Services;
import com.society.dao.implementation.ServicesDAO;

@Service
public class ServicesService {
	public ServicesDAO serviceDao= new  ServicesDAO();
	public void createService(Services service) throws ClassNotFoundException, SQLException {
		serviceDao.addService(service);
	}
	public List<Services> retriveAllServices() throws ClassNotFoundException, SQLException{
		return serviceDao.getAllServices();
	}
	public List<Services> retriveServiceByUser(String userId) throws ClassNotFoundException, SQLException{
		return serviceDao.getServiceByUserId(userId);
	}
	
	public void updateService()throws ClassNotFoundException, SQLException {
		//will be implement later
	}
	public void deleteServiceById(String serviceId)throws ClassNotFoundException, SQLException {
		serviceDao.deleteService(serviceId);
	}
}
