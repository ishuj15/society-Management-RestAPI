package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Services;
import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ServicesDAO;
import com.society.exceptions.ServiceException;
import com.society.exceptions.UserException;
import com.society.serviceInterface.ServicesServiceInterface;

@Service
public class ServicesService implements ServicesServiceInterface {
	@Autowired
	public ServicesDAO serviceDao;
	
	@Autowired
	public UserService userService;
	@Override
	public boolean createService(Services service) throws ClassNotFoundException, SQLException {
		
		try{
			User user= userService.retriveUserById(service.getUserId());
			if(user==null)
				throw new UserException("User not found");
			
			serviceDao.addService(service);
			return true;	
		}
		catch(Exception e){
			throw e; 
		}
		
} 
	@Override
	public List<Services> retriveAllServices() throws ClassNotFoundException, SQLException{
		try {
			List<Services> list = serviceDao.getAllServices();
			if(!list.isEmpty())
				return list;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
			}
		return null;

	}
	@Override
	public List<Services> retriveServiceByUser(String userId) throws ClassNotFoundException, SQLException{
		List<Services> list =  serviceDao.getServiceByUserId(userId);
//		if(list.isEmpty())
//			throw new ServiceException(ApiMessages.SERVICE_NOT_FOUND);
//		else 
			return list;

	}
	@Override
	public boolean updateService(String serviceId, Services service)throws ClassNotFoundException, SQLException {
		//will be implement later
		if(serviceDao.updateService(serviceId, service))
		return true;
		
		return false;

	}
	@Override
	public boolean deleteServiceById(String serviceId)throws ClassNotFoundException, SQLException {
		if(!serviceDao.deleteService(serviceId))
			throw new ServiceException(ApiMessages.UNABLE_TO_DELETE_SERVICE);
		return true;

	}
}
