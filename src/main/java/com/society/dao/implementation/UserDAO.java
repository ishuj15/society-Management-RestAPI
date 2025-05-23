package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.User;
import com.society.daoInterface.UserInterface;
import com.society.exceptions.UserException;
@Repository
public class UserDAO extends GenericDAO<User> implements UserInterface {


	@Override
	protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setIdUser(resultSet.getString("idUser"));
		user.setUserName(resultSet.getString("userName"));
		user.setUserRole(resultSet.getString("userRole"));
		user.setPassword(resultSet.getString("password"));
		user.setPhoneNo(resultSet.getString("phoneNo"));
		user.setEmail(resultSet.getString("email"));
		user.setAddress(resultSet.getString("address"));
		user.setQrImage(resultSet.getString("qrImage"));
		user.setQrToken(resultSet.getString("qrToken"));
		return user;
	}
	public boolean addUser(User user) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
				"INSERT INTO user ( idUser, userName,  userRole, password,phoneNo,email,address,qrImage,qrToken) VALUES ('%s','%s','%s','%s', '%s','%s','%s','%s','%s')",
				user.getIdUser(), user.getUserName(), user.getUserRole(), user.getPassword(), user.getPhoneNo(),
				user.getEmail(), user.getAddress() , user.getQrImage(), user.getQrToken());
		return executeQuery(sqlQuery);
	}

	public User getUserById(String userId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM user WHERE idUser = \"" + userId + "\"";
		return executeGetQuery(sqlQuery);
	}

	public User getUserByUserName(String userName) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM user WHERE userName = \"" + userName + "\"";
		return executeGetQuery(sqlQuery);
	}

	public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM user WHERE idUser = \"" + userId + "\"";
		return executeQuery(sqlQuery);
	}

	public List<User> getAllUsers(int offset,int limit) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM user LIMIT " + limit + " OFFSET " + offset;
		//String query = "SELECT * FROM account LIMIT ? OFFSET ?";
      //  return executeGetAllQuery(query, limit, offset);
//		String sqlQuery = "SELECT * FROM user";
		return executeGetAllQuery(sqlQuery);	
	}
	public boolean updateUser(String userId, User user)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
			    "UPDATE user SET email = '%s', address = '%s', phoneNo = '%s',password = '%s' WHERE idUser = '%s'",
			    user.getEmail(), user.getAddress(), user.getPhoneNo(), user.getPassword(), userId
			);

		return executeQuery(sqlQuery);
	}
	public List<User> getUserByUserType(String userType) throws SQLException, ClassNotFoundException{
		try {
			String sqlQuery = "SELECT * FROM user where userRole= \"" +userType +"\"";
			return executeGetAllQuery(sqlQuery);	
		}
		catch(Exception e ) {
			throw new UserException("Faild at dao layer");		}
	}
	

}
