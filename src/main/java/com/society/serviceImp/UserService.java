package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserInterface userDAO;

    @Override
    public User getUserByUserName(String username) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Fetching user by username: {}", username);
            return userDAO.getUserByUserName(username);
        } catch (Exception e) {
            logger.error("Error while fetching user by username: {}", username, e);
            throw new UserException("Failed to retrieve user by username");
        }
    }

    @Override
    public boolean createUser(User user) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Creating user with username: {}", user.getUserName());
            user.setPassword(Helper.hashPassword(user.getPassword()));
            if (!userDAO.addUser(user)) {
                logger.error("Failed to create user with username: {}", user.getUserName());
                throw new UserException(ApiMessages.UNABLE_TO_CREATE_USER);
            }
            logger.info("User created successfully: {}", user.getUserName());
            return true;
        } catch (Exception e) {
            logger.error("Error while creating user: {}", user.getUserName(), e);
            throw new UserException("Failed to create user");
        }
    }

    @Override
    public List<User> retriveAllUsers(int page, int size) throws SQLException, ClassNotFoundException {
        try {
            logger.info("Retrieving users with pagination - page: {}, size: {}", page, size);
            int offset = page * size;
            List<User> users = userDAO.getAllUsers(offset, size);
            if (users == null) {
                logger.error("No users found for the given pagination parameters.");
                throw new UserException(ApiMessages.USER_NOT_FOUND);
            }
            logger.info("Retrieved {} users.", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error while retrieving users with pagination - page: {}, size: {}", page, size, e);
            throw new UserException("Failed to retrieve users");
        }
    }

    @Override
    public User retriveUserById(String userId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving user by ID: {}", userId);
            User user = userDAO.getUserById(userId);
            if (user == null) {
                logger.error("User not found with ID: {}", userId);
                throw new UserException(ApiMessages.USER_NOT_FOUND);
            }
            logger.info("User retrieved: {}", user.getUserName());
            return user;
        } catch (Exception e) {
            logger.error("Error while retrieving user by ID: {}", userId, e);
            throw new UserException("Failed to retrieve user by ID");
        }
    }

    @Override
    public boolean updateUser(String userId, User user) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Updating user with ID: {}", userId);
            User existingUser = userDAO.getUserById(userId);
            if (existingUser == null) {
                logger.error("Unable to update user. User not found with ID: {}", userId);
                throw new UserException(ApiMessages.UNABLE_TO_UPDATE_USER);
            }
            if (user.getAddress() != null) userDAO.updateUser(userId, "address", user.getAddress());
            if (user.getEmail() != null) userDAO.updateUser(userId, "email", user.getEmail());
            if (user.getPhoneNo() != null) userDAO.updateUser(userId, "phoneNo", user.getPhoneNo());
            if (user.getPassword() != null) userDAO.updateUser(userId, "password", user.getPassword());
            logger.info("User updated successfully: {}", userId);
            return true;
        } catch (Exception e) {
            logger.error("Error while updating user with ID: {}", userId, e);
            throw new UserException("Failed to update user");
        }
    }

    @Override
    public boolean deleteUser(String userId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting user with ID: {}", userId);
            User user = userDAO.getUserById(userId);
            if (user == null || user.getUserRole().toLowerCase().equals(str.admin)) {
                logger.error("Unable to delete user. Invalid user ID or role is admin: {}", userId);
                throw new UserException(ApiMessages.UNABLE_TO_DELETE_USER);
            }
            userDAO.deleteUser(userId);
            logger.info("User deleted successfully: {}", userId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", userId, e);
            throw new UserException("Failed to delete user");
        }
    }

    @Override
    public List<User> printUsernameList(String userType) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Fetching users by user type: {}", userType);
            List<User> listOfUser = userDAO.getUserByUserType(userType);
            logger.info("Retrieved {} users of type: {}", listOfUser.size(), userType);
            return listOfUser;
        } catch (Exception e) {
            logger.error("Error while fetching users by type: {}", userType, e);
            throw new UserException("Failed to retrieve users by type");
        }
    }

    @Override
    public User SelctUserfromUsernameList(List<User> list) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Selecting user from username list of size: {}", list.size());
            int choice = Helper.choiceInput(list.size());
            User selectedUser = list.get(choice - 1);
            logger.info("User selected: {}", selectedUser.getUserName());
            return selectedUser;
        } catch (Exception e) {
            logger.error("Error while selecting user from username list", e);
            throw new UserException("Failed to select user from list");
        }
    }
}
