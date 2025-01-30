	package com.society.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.exceptions.UserException;
import com.society.serviceImp.UserService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")

@RestController 
public class UserController { 
	@Autowired
	private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 
		
	@PostMapping("/api/auth/user")
	public ResponseEntity<Object> createUser(@Valid  @RequestBody  User user) throws ClassNotFoundException, SQLException {
//		System.out.println(user.getPassword());

		user.setIdUser(Helper.generateUniqueId());
        logger.info("Request to create user: {}", user);

		if(	userService.createUser(user))
		{
			logger.info("User created successfully: {}", user.getIdUser());
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.CREATED, ApiMessages.USER_CREATED,  user);

		}
		else 
		{
            logger.error("Unable to create user: {}", user);
            throw new UserException(ApiMessages.UNABLE_TO_CREATE_USER);

		}
	} 
	//without pagination
//	@GetMapping(path="/users")
//	public ResponseEntity<Object>  retriveAllUsers(
//			@RequestParam(required= false) String userRole ,
//			@RequestParam (required= false) String address	,
//			
//			) throws ClassNotFoundException, SQLException {
//		List<User> users= userService.retriveAllUsers() ;	
//		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  users);
//

//	}
	
	//with pagination
	@GetMapping(path="/users")
    public ResponseEntity<Object> retriveAllUsers(
           // @RequestParam(required = false) 
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws ClassNotFoundException, SQLException {
        logger.info("Request to retrieve all users - Page: {}, Size: {}", page, size);

        	List<User> users= userService.retriveAllUsers(page,size) ;	

        	if(users!=null)
        	{
                logger.info(ApiMessages.FETCHED);

                return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  users);
        	}
        	else
        	{
                logger.warn(ApiMessages.USER_NOT_FOUND);
                return ApiResponseHandler.buildResponse(ApiResponseStatus.ERROR, HttpStatus.NOT_FOUND, ApiMessages.USER_NOT_FOUND, null);
        	}		        
    }
	
	@GetMapping(path="/user/{userId}")
	public ResponseEntity<Object> retriveUserById(@PathVariable String userId) throws ClassNotFoundException, SQLException {
        logger.info("Request to retrieve user by ID: {}", userId);

		User user= userService.retriveUserById(userId);
		if(user==null)
		{
            logger.warn("User not found: {}", userId);
            throw new UserException(ApiMessages.USER_NOT_FOUND);
		}
		else
		{
            logger.info("User retrieved successfully: {}", userId);
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  user);
		}
	}
	 
	@PutMapping(path="/user/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody User user) throws ClassNotFoundException, SQLException {
        logger.info("Request to update user: {}", userId);

		if(userService.updateUser(userId,user))
		{
            logger.info("User updated successfully: {}", userId);

            return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.USER_UPDATED,  null);
		}
		else
		{
            logger.error("Unable to update user: {}", userId);
            throw new UserException(ApiMessages.UNABLE_TO_UPDATE_USER);
		}
	}
 
	@DeleteMapping(path="/user/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable String userId) throws ClassNotFoundException, SQLException {
        logger.info("Request to delete user: {}", userId);

		if( userService.deleteUser(userId))
		{
            logger.info("User deleted successfully: {}", userId);
            return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.USER_DELETED,  null);
		}
			else
			{
	            logger.error("Unable to delete user: {}", userId);
	            throw  new UserException(ApiMessages.UNABLE_TO_DELETE_USER);
			}
	}
	// getUserbyadmin
	//getUsernameList()
	 @GetMapping(path="/userName")
	 public ResponseEntity<Object> getuserName() throws ClassNotFoundException , SQLException
	 {
	        logger.info("Request to access username list by admin: {}");
	        try {
	        List<User> list= userService.UsernameList(str.resident);
	        logger.info("UserName list retrived successfully");
            return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

	        }
	       catch(Exception e)
	        {
	            logger.error("Unable to retrive user list: {}");
	            throw  new UserException("Unable to fetch username lists");
	        }
	 }
	 @GetMapping(path ="/user/userName/{userName}")
	 public ResponseEntity<Object> getUserByUserName( @PathVariable String userName) throws ClassNotFoundException , SQLException{
		 User user= userService.getUserByUserName(userName);
         return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  user);

	 }

}
