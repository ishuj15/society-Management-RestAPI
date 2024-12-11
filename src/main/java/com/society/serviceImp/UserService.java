package com.society.serviceImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.daoInterface.UserInterface;
import com.society.exceptions.UserException;
import com.society.serviceInterface.UserServiceInterface;
import com.society.util.Helper;
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
		if(existingUser==null)
			throw new UserException(ApiMessages.UNABLE_TO_UPDATE_USER);
		else
		{
			if(user.getAddress()!=null)
				userDAO.updateUser(userId, "address", user.getAddress());
			if(user.getEmail()!=null)
				userDAO.updateUser(userId, "email", user.getEmail());
			if(user.getPhoneNo()!=null)
				userDAO.updateUser(userId, "phoneNo", user.getPhoneNo());
			if(user.getPassword()!=null)
				userDAO.updateUser(userId, "password", user.getPassword());
			
			return true;
		}
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
		//return false;
		
	}
	@Override
	public List<User> printUsernameList(String userType) throws ClassNotFoundException, SQLException {
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
