package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Visitor;

public interface VisitorServiceInterface {
	public boolean createVisitor(Visitor visitor) throws SQLException, ClassNotFoundException ;
	public List<Visitor> retriveVisitorByUser(String id) throws SQLException, ClassNotFoundException ;
	public List<Visitor> retriveAllVisitors() throws SQLException, ClassNotFoundException ;
	public boolean updateVisitor()
			throws SQLException, ClassNotFoundException ;
	public boolean deleteVisitorById(String visitorId) throws SQLException, ClassNotFoundException ;
	public List<Visitor> getAllPendingRequests(String userId, String approvalReq) throws SQLException, ClassNotFoundException ;


}
