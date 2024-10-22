package com.society.daoInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Services;

public interface ServiceInterface {
	public boolean addService(Services service) throws SQLException, ClassNotFoundException ;
	public List<Services> getServiceByUserId(String userId) throws SQLException, ClassNotFoundException ;
	public List<Services> getAllServices() throws SQLException, ClassNotFoundException ;
	public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException ;
	public boolean updateService(String serviceId, String columnToUpdate, String newValue)throws SQLException, ClassNotFoundException ;

}
