package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.User;

public interface UserServiceInterface {
	//public  boolean verifyPassword(String enteredPassword, String storedHashedPassword) ;
	public void createUser(User user) throws SQLException, ClassNotFoundException ;
	public void deleteUser(String userId) throws SQLException, ClassNotFoundException ;
	//public  void login(String userName, String password) throws SQLException, InterruptedException, ClassNotFoundException ;
	public void updateUser(String userId, User user) throws ClassNotFoundException, SQLException;

	public User retriveUserById(String userId) throws ClassNotFoundException, SQLException ;
	public List<User> retriveAllUsers() throws SQLException, ClassNotFoundException ;
	public User getUserByUserName(String userName) throws SQLException, ClassNotFoundException ;
//	public  User getUsernameList() throws ClassNotFoundException, SQLException ;
}
