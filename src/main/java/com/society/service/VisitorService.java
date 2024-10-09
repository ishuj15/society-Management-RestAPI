package com.society.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.Visitor;
import com.society.dao.implementation.VisitorDAO;

@Service
public class VisitorService {
	public VisitorDAO visitorDao= new VisitorDAO();
	public void createVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
		visitorDao.addVisitor(visitor);
		
	}
	
	public List<Visitor> retriveAllVisitors() throws ClassNotFoundException, SQLException  {
		return visitorDao.getAllVisitors();
	}
	public List<Visitor> retriveVisitorByUser(String userId) throws ClassNotFoundException, SQLException  {
		return visitorDao.getVisitorById(userId);
	}
	
	public void updateVisitor() throws ClassNotFoundException, SQLException  {
		//not now
	}
	public void deleteVisitorById(String visitorId) throws ClassNotFoundException, SQLException  {
		visitorDao.deleteVisitor(visitorId);
	}
	public List<Visitor> getAllPendingRequests(String userId)  throws ClassNotFoundException, SQLException {
		return visitorDao.getAllpendingRequests(userId, "Pending");
	}

}
