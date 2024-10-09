package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.Services;
import com.society.daoInterface.ServiceInterface;
@Repository
public class ServicesDAO extends GenericDAO<Services> implements ServiceInterface{
//	public ServicesDAO()
//	{
//		super();
//	}
	@Override
	protected Services mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Services service = new Services();
		service.setIdServices(resultSet.getString("idservices"));
		service.setUserId(resultSet.getString("userId"));
		service.setServiceName(resultSet.getString("serviceName"));
		service.setDescription(resultSet.getString("Description"));
		service.setStatus(resultSet.getString("status"));
		return service;
	}

	public boolean addService(Services service) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
				"INSERT INTO services (idServices, userId, serviceName, Description,status) VALUES ('%s','%s','%s','%s','%s')",
				service.getIdServices(), service.getUserId(), service.getServiceName(), service.getDescription(),
				service.getStatus());
		return executeQuery(sqlQuery);
	}

	public List<Services> getServiceByUserId(String userId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM services WHERE userId = \"" + userId + "\"";
		return executeGetAllQuery(sqlQuery);
	}

	public List<Services> getAllServices() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM services";
		return executeGetAllQuery(sqlQuery);
	}

	public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM services WHERE idservices = \"" + serviceId + "\"";
		return executeQuery(sqlQuery);
	}

	public boolean updateService(String serviceId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("UPDATE services SET %s = '%s' WHERE idservices = '%s'", columnToUpdate,
				newValue, serviceId);
		return executeQuery(sqlQuery);
	}
}