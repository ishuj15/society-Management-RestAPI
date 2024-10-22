package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Services;

public interface ServicesServiceInterface {
	public void createService(Services service) throws SQLException, ClassNotFoundException ;
	public List<Services> retriveServiceByUser(String idUser) throws SQLException, ClassNotFoundException ;
	public List<Services> retriveAllServices() throws SQLException, ClassNotFoundException ;
	public void updateService()
			throws SQLException, ClassNotFoundException ;
	public void deleteServiceById(String id) throws SQLException, ClassNotFoundException ;
	//public Services getService()throws SQLException, ClassNotFoundException ;
	
}
