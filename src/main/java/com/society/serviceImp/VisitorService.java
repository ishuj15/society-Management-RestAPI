package com.society.serviceImp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.society.Model.User;
import com.society.Model.Visitor;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.VisitorDAO;
import com.society.exceptions.UserException;
import com.society.exceptions.VisitorException;
import com.society.serviceInterface.VisitorServiceInterface;
import com.society.util.Helper;
import com.society.util.QrCodeGenerator;
import com.society.util.str;

@Service
public class VisitorService implements VisitorServiceInterface {
	@Autowired
	public VisitorDAO visitorDao;
	
	@Autowired
	public UserService userService;
	@Override
	public boolean createVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
	    try {
	        User user = userService.retriveUserById(visitor.getUserId());
	        if (user == null) {
	            throw new UserException("");
	        }
	        visitor.setIdVisitor(Helper.generateUniqueId());
	        String token = Helper.generateUniqueId() ;
            visitor.setToken(token);
            byte[] qrCodeImage = null;
			try {
				qrCodeImage = QrCodeGenerator.generateQRCodeImage(token);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            // You can save the image or send its Base64 to the frontend
            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeImage);
            visitor.setQrCodeBase64(qrCodeBase64);
       
            if (user.getUserRole().equalsIgnoreCase(str.resident)) {
            	System.out.println(user.getUserRole());
	            visitor.setStatus(str.approved);
	            // For QR code 
	          
	           
	        } else {
	            visitor.setStatus(str.pending);
	        }

	        if (!visitorDao.addVisitor(visitor)) {
	            throw new VisitorException(ApiMessages.UNABLE_TO_CREATE_VISITOR);
	        }
	        return true;
	    } catch (WriterException e) {
	        // Handle exception (for example, log it and rethrow as a custom exception)
	        throw new VisitorException("Error generating QR code");
	    }
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
			List<Visitor> list= visitorDao.getVisitorByUserId(userId);
			return list;
		}
		catch(Exception e) {
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
		} 
	}
	@Override
	public boolean updateVisitor(Visitor visitor, String columnToUpdate, String newValue) throws ClassNotFoundException, SQLException  {
		//not now
	if(	visitorDao.updateVisitor(visitor.getIdVisitor(), columnToUpdate, newValue))
	{ 
		return true;
	}
		return false;
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
			List<Visitor> list= visitorDao.getAllpendingRequests(userId, "Pending");
			return list;
		}
		catch(Exception e)
		{
			throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
		}
	}
	public boolean updateVisitorStatus(String visitorId, String status) throws ClassNotFoundException, SQLException  {
//		System.out.println(status);
//		Visitor DbVisitor = visitorDao.getVisitorByVisitorId(visitorId);
		
			visitorDao.updateVisitorStatus(visitorId,status);
//			System.out.println("service");

		
	return true;

		}
	@Override
	public Visitor getVisitorByVisitorId(String visitorId) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	public Visitor verifyVisitor(String token) throws ClassNotFoundException, SQLException {
		
		return visitorDao.verifyVisitor(token);
		// TODO Auto-generated method stub
		
	}

}
