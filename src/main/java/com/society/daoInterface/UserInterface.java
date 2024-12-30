package com.society.daoInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.User;

public interface UserInterface {
	public boolean addUser(User user) throws SQLException, ClassNotFoundException;
	public User getUserById(String userId) throws SQLException, ClassNotFoundException;
	public User getUserByUserName(String userName) throws SQLException, ClassNotFoundException ;
	public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException ;
	//public List<User> getAllUsers() throws SQLException, ClassNotFoundException;
	public List<User> getAllUsers(int page,int size) throws SQLException, ClassNotFoundException;
	public boolean updateUser(String userId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException;
	public List<User> getUserByUserType(String userType) throws SQLException, ClassNotFoundException;

}