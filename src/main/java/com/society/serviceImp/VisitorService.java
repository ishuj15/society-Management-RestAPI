package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Visitor;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.VisitorDAO;
import com.society.exceptions.VisitorException;
import com.society.serviceInterface.VisitorServiceInterface;

@Service
public class VisitorService implements VisitorServiceInterface {
	@Autowired
	public VisitorDAO visitorDao;
	@Override
	public boolean createVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
		if(!visitorDao.addVisitor(visitor))
			throw new VisitorException(ApiMessages.UNABLE_TO_CREATE_VISITOR);
		return true;

	}
	@Override
	public List<Visitor> retriveAllVisitors() throws ClassNotFoundException, SQLException  {
		List<Visitor> list= visitorDao.getAllVisitors();
		if(list.isEmpty())
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
			
		else
			return list;
	}
	@Override
	public List<Visitor> retriveVisitorByUser(String userId) throws ClassNotFoundException, SQLException  {
		List<Visitor> list= visitorDao.getVisitorById(userId);
		
		if(list.isEmpty())
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
			
		else
			return list;
	} 
	@Override
	public boolean updateVisitor() throws ClassNotFoundException, SQLException  {
		//not now
		return true;

	}
	@Override
	public boolean deleteVisitorById(String visitorId) throws ClassNotFoundException, SQLException  {
		if(!visitorDao.deleteVisitor(visitorId))
			throw new VisitorException(ApiMessages.UNABLE_TO_DELETE_VISITOR);
		return true;

	}
	@Override
	public List<Visitor> getAllPendingRequests(String userId, String status)  throws ClassNotFoundException, SQLException {
		List<Visitor> list= visitorDao.getAllpendingRequests(userId, status);
		
		if(list.isEmpty())
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
			
		else
			return list;
	}

}
