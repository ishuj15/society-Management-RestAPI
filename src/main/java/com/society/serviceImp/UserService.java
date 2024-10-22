package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.UserDAO;
import com.society.exceptions.UserNotFoundException;
import com.society.serviceInterface.UserServiceInterface;
import com.society.util.str;

@Service
public class UserService implements UserServiceInterface {
	@Autowired
	public UserDAO userDAO ;
	@Override
	public User getUserByUserName(String username) throws ClassNotFoundException, SQLException
	{
		return userDAO.getUserByUserName(username);
	}
	@Override
	public void createUser(User user) throws ClassNotFoundException, SQLException {
		if(userDAO.addUser(user))
			throw new UserNotFoundException(ApiMessages.UNABLE_TO_CREATE_USER);
	}
	@Override
	public List<User> retriveAllUsers() throws ClassNotFoundException, SQLException {
		 List<User> users= userDAO.getAllUsers();
		 if(users.isEmpty())
				throw new UserNotFoundException(ApiMessages.USER_NOT_FOUND);
		 else
			 return users; 
	}
	@Override
	public User retriveUserById(String userId) throws ClassNotFoundException, SQLException {
		User user= userDAO.getUserById(userId);
		if(user.equals(null))
			throw new UserNotFoundException(ApiMessages.USER_NOT_FOUND);
		else
			return user;		
	}
	@Override
	public void updateUser(String userId,User user) throws ClassNotFoundException, SQLException {
		User existingUser= userDAO.getUserById(userId);
		if(existingUser==null)
			throw new UserNotFoundException(ApiMessages.UNABLE_TO_UPDATE_USER);
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
			
		}
	}
	@Override
	public void deleteUser(String userId) throws ClassNotFoundException, SQLException {
		User user = userDAO.getUserById(userId);
		if (user.equals(null) || user.getUserRole().toLowerCase().equals(str.admin))
			throw new UserNotFoundException(ApiMessages.UNABLE_TO_DELETE_USER);
		
		else {
			userDAO.deleteUser(userId);
		}
	}
}
