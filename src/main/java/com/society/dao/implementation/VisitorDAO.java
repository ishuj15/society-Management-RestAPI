package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.Visitor;
import com.society.daoInterface.VisitorInterface;
@Repository
public class VisitorDAO extends GenericDAO<Visitor> implements VisitorInterface {

	@Override
	protected Visitor mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Visitor visitor = new Visitor();
		visitor.setIdVisitor(resultSet.getString("idvisitor"));
		visitor.setUserId(resultSet.getString("userId"));
		visitor.setName(resultSet.getString("name"));
		visitor.setArrivalDate(resultSet.getString("date_of_arrival"));
		visitor.setPurpose(resultSet.getString("purpose"));
		visitor.setStatus(resultSet.getString("approvalReq"));
		visitor.setArrivalTime(resultSet.getString("arrivalTime"));
		visitor.setDepartureTime(resultSet.getString("departureTime"));
		visitor.setContactNo(resultSet.getString("contact"));
		visitor.setDep_date(resultSet.getString("departure_date"));
		return visitor;
	}

	public boolean addVisitor(Visitor visitor) throws SQLException, ClassNotFoundException {

		String sqlQuery = String.format(
				"INSERT INTO visitor (idvisitor, userId, name,contact, purpose, date_of_arrival,"
						+ "arrivalTime,departure_date,departureTime, approvalReq) "
						+ "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
				visitor.getIdVisitor(), visitor.getUserId(), visitor.getName(), visitor.getContactNo(),
				visitor.getPurpose(), visitor.getArrivalDate().toString(), visitor.getArrivalTime(),
				visitor.getDep_date().toString(), visitor.getDepartureTime(), visitor.getStatus());

		return executeQuery(sqlQuery);
	}

	public List<Visitor> getVisitorById(String userId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM visitor WHERE userId = \"" + userId + "\"";
		return executeGetAllQuery(sqlQuery);
	}

	public List<Visitor> getAllVisitors() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM visitor";
		return executeGetAllQuery(sqlQuery);
	}

	public boolean deleteVisitor(String visitorId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM Visitor WHERE idvisitor = \"" + visitorId + "\"";
		return executeQuery(sqlQuery);
	}

	public boolean updateVisitor(String visitorId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = "UPDATE visitor SET " + columnToUpdate + " = '" + newValue + "' WHERE idvisitor = '" + visitorId + "'";
//
//		String sqlQuery = String.format("UPDATE visitor SET '%s' = '%s' WHERE idvisitor = '%s'", columnToUpdate,
//				newValue, visitorId);
		return executeQuery(sqlQuery);
	}
	public List<Visitor> getAllpendingRequests(String userId, String apr) throws ClassNotFoundException, SQLException {
		String sqlQuery = String.format("SELECT * FROM visitor WHERE userId='%s' AND approvalReq='%s'", userId, apr);
		return executeGetAllQuery(sqlQuery);
	}

}
