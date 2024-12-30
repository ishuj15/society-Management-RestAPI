package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.User;
import com.society.Model.Visitor;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.VisitorDAO;
import com.society.exceptions.UserException;
import com.society.exceptions.VisitorException;
import com.society.serviceInterface.VisitorServiceInterface;
import com.society.util.Helper;
import com.society.util.str;

@Service
public class VisitorService implements VisitorServiceInterface {
	@Autowired
	public VisitorDAO visitorDao;
	
	@Autowired
	public UserService userService;
	@Override
	public boolean createVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
		
		User user = userService.retriveUserById(visitor.getUserId());
		if(user==null)
			throw new UserException("");
		
		visitor.setIdVisitor(Helper.generateUniqueId());

		if(user.getUserRole()==str.resident)
			visitor.setStatus(str.approved);
		else
			visitor.setStatus(str.pending);
		
		if(  !visitorDao.addVisitor(visitor))
			throw new VisitorException(ApiMessages.UNABLE_TO_CREATE_VISITOR);
		return true;

	}
	@Override
	public List<Visitor> retriveAllVisitors() throws ClassNotFoundException, SQLException  {
		try {
			List<Visitor> list= visitorDao.getAllVisitors();
			return list;			
		}
		catch(Exception e) {
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
	}
	}
	@Override
	public List<Visitor> retriveVisitorByUser(String userId) throws ClassNotFoundException, SQLException  {
		
		try {
			List<Visitor> list= visitorDao.getVisitorById(userId);
			return list;
		}
		catch(Exception e) {
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
		} 
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
		
		try {
			List<Visitor> list= visitorDao.getAllpendingRequests(userId, status);
			return list;
		}
		catch(Exception e)
		{
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
		}
	}

}