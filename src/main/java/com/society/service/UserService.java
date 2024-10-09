package com.society.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.User;
import com.society.dao.implementation.UserDAO;
import com.society.util.str;

@Service
public class UserService {
	public UserDAO userDAO = new UserDAO();
	
	public void createUser(User user) throws ClassNotFoundException, SQLException {
		userDAO.addUser(user);
	}
	
	public List<User> retriveAllUsers() throws ClassNotFoundException, SQLException {
		return  userDAO.getAllUsers();	 
	}
	public User retriveUserById(String userId) throws ClassNotFoundException, SQLException {
		return userDAO.getUserById(userId);
	}
	
	public ArrayList<String> updateUser(User user) throws ClassNotFoundException, SQLException {
		
		ArrayList<String> list= new ArrayList<>();
	
		 if(user.getAddress()!=null)
		{
			userDAO.updateUser(user.getIdUser(), "address", user.getAddress());
			list.add("Address updated successfully");
			
		}
		  if(user.getEmail()!=null)
			{
				userDAO.updateUser(user.getIdUser(), "email", user.getEmail());
				list.add("Email updated successfully");

			}
		  if(user.getPassword()!=null)
			{
				userDAO.updateUser(user.getIdUser(), "password", user.getPassword());
				list.add("Password updated successfully");

			}
		 if(user.getPhoneNo()!=null)
			{
				userDAO.updateUser(user.getIdUser(), "phoneNo", user.getPhoneNo());
				list.add("Phone No updated successfully");
			}

		 else
		 {
			 list.add( "unable to update");
			 return list;
		 }
		  return list;
		
		
	}
	public String deleteUser(String userId) throws ClassNotFoundException, SQLException {
		User user=userDAO.getUserById(userId);
		if(user==null)
			return "No user found to delete";
		else if (user.getUserRole().toLowerCase().equals(str.admin) ) {
			return " Not able to delete user";
		}
		
		else {
			userDAO.deleteUser(userId);
			return "User deleted successfully";
		}
		
	}

}
