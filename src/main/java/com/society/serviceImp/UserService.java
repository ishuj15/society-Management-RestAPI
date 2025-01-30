package com.society.serviceImp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.google.zxing.WriterException;
import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.daoInterface.UserInterface;
import com.society.exceptions.UserException;
import com.society.serviceInterface.UserServiceInterface;
import com.society.util.Helper;
import com.society.util.QrCodeGenerator;
import com.society.util.str;

@Service 
public class UserService implements UserServiceInterface {
	@Autowired
	public UserInterface userDAO ;
	@Override
	public User getUserByUserName(String username) throws ClassNotFoundException, SQLException
	{
		return userDAO.getUserByUserName(username);
	}
	@Override
	public boolean createUser(User user) throws ClassNotFoundException, SQLException {
		user.setPassword(Helper.hashPassword(user.getPassword()));
		
		// Generate qr code
		
		  String secretKey = Helper.generateUniqueId();
		  String base32Secret=null;
		try {
			base32Secret = QrCodeGenerator.convertBase16ToBase32(secretKey);
//			System.out.println("Base32 Secret: " + base32Secret); FNRSS2JCRY======

		} catch (DecoderException e) {
			e.printStackTrace();
		}

	       String issuer = "MyApp";
	        user.setQrToken(base32Secret);
	        String otpAuthURL = String.format(
	                "otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
	                issuer, user.getUserName(), base32Secret, issuer);

	        byte[] qrImage = null;
	            try {
					 qrImage =  QrCodeGenerator.generateQRCodeImageUser(otpAuthURL, 300, 300);
					
//					ByteArrayResource resource = new ByteArrayResource(qrImage);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            String qrImage2 = Base64.getEncoder().encodeToString(qrImage);
	            user.setQrImage(qrImage2);
		if(!userDAO.addUser(user))
			throw new UserException(ApiMessages.UNABLE_TO_CREATE_USER);
		return true; 
	}
	@Override
	public List<User> retriveAllUsers(int page,	int size) throws  SQLException, ClassNotFoundException {
		int offset = page * size;
		 List<User> users= userDAO.getAllUsers(offset, size);
		 if(users==null )
				throw new UserException(ApiMessages.USER_NOT_FOUND);
		 else
			 return users; 
	}
	@Override
	public User retriveUserById(String userId) throws ClassNotFoundException, SQLException {
		User user= userDAO.getUserById(userId);
		if(user==null)
			throw new UserException(ApiMessages.USER_NOT_FOUND);
		else
			return user;		
	}
	@Override
	public boolean updateUser(String userId,User user) throws ClassNotFoundException, SQLException {
		User existingUser= userDAO.getUserById(userId);
		
		if(user.getPassword()!=null)
		user.setPassword(Helper.hashPassword(user.getPassword()));
		if(existingUser==null)
			throw new UserException(ApiMessages.UNABLE_TO_UPDATE_USER);
		if(userDAO.updateUser(userId, user))
			return true;
		return false;
		
	}
	@Override
	public boolean deleteUser(String userId) throws ClassNotFoundException, SQLException {
		User user = userDAO.getUserById(userId);
		if (user==null || user.getUserRole().toLowerCase().equals(str.admin))
			throw new UserException(ApiMessages.UNABLE_TO_DELETE_USER);
		
		else {
			userDAO.deleteUser(userId);
			return true;
		}	
	}
	@Override
	public List<User> UsernameList(String userType) throws ClassNotFoundException, SQLException {
		try {
		List<User> listOfUser = userDAO.getUserByUserType(userType);
		return listOfUser;
		}
		catch(Exception e) {
			throw new UserException("Faild at service layer");
		}
		
	}
	@Override
	public User SelctUserfromUsernameList(List<User> list) throws ClassNotFoundException, SQLException {
		int choice = Helper.choiceInput(list.size());
		User selectedUser = list.get(choice - 1);
		return selectedUser;
	}
} 
