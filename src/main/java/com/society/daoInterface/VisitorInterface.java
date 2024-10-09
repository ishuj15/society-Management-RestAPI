package com.society.daoInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Visitor;

public interface VisitorInterface {
	public boolean addVisitor(Visitor visitor) throws SQLException, ClassNotFoundException ;
	public List<Visitor> getVisitorById(String userId) throws SQLException, ClassNotFoundException ;
	public List<Visitor> getAllVisitors() throws SQLException, ClassNotFoundException;
	public boolean deleteVisitor(String visitorId) throws SQLException, ClassNotFoundException;
	public boolean updateVisitor(String visitorId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException;
	public List<Visitor> getAllpendingRequests(String userId, String apr) throws ClassNotFoundException, SQLException;
}
