package com.society.serviceInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.zxing.WriterException;
import com.society.Model.Visitor;

public interface VisitorServiceInterface {
	public boolean createVisitor(Visitor visitor) throws SQLException, ClassNotFoundException, WriterException, IOException ;
	public List<Visitor> retriveVisitorByUser(String id) throws SQLException, ClassNotFoundException ;
	public List<Visitor> retriveAllVisitors() throws SQLException, ClassNotFoundException ;
	public boolean updateVisitorStatus(String visitor, String status)
			throws SQLException, ClassNotFoundException ;
	public boolean deleteVisitorById(String visitorId) throws SQLException, ClassNotFoundException ;
	public List<Visitor> getAllPendingRequests(String userId, String approvalReq) throws SQLException, ClassNotFoundException ;
	public boolean updateVisitor(Visitor visitor, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException ;
	
	public Visitor getVisitorByVisitorId(String visitorId) throws SQLException, ClassNotFoundException ;
//	public Visitor getVisitorByUserId(String userId) throws SQLException, ClassNotFoundException ;

}
