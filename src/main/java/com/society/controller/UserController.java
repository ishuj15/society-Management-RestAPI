package com.society.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.User;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.UserService;

@RestController 
public class UserController { 
	@Autowired
	private UserService userService;
		
	@PostMapping("/api/auth/user")
	public ResponseEntity<Object> createUser(  @RequestBody  User user) throws ClassNotFoundException, SQLException {
		userService.createUser(user);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.USER_CREATED,  null);

	}
	//done
	@GetMapping(path="/users")
	public ResponseEntity<Object>  retriveAllUsers(
			@RequestParam(required= false) String userRole ,
			@RequestParam (required= false) String address	
			
//			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//			@RequestParam(value = "limit", defaultValue = "5", required = false) Integer limit
			) throws ClassNotFoundException, SQLException {
		List<User> users= userService.retriveAllUsers() ;	
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  users);

//		int totalCount = (int) userService.countAllUsers();
//		int totalPages = (int) (totalCount + limit - 1) / limit;
//		return null;
//		ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK,
//				ApiMessages.USERS_FETCHED_SUCCESSFULLY, user, pageNumber, limit, Integer.valueOf(totalCount),
//				Integer.valueOf(totalPages));
	}
	//done
	@GetMapping(path="/user/{userId}")
	public ResponseEntity<Object> retriveUserById(@PathVariable String userId) throws ClassNotFoundException, SQLException {
		User user= userService.retriveUserById(userId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  user);

		
	}
	//done
	@PatchMapping(path="/user/{userId}")
	public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody User user) throws ClassNotFoundException, SQLException {
		userService.updateUser(userId,user);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.USER_UPDATED,  null);

	}
	//done
	@DeleteMapping(path="/user/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable String userId) throws ClassNotFoundException, SQLException {
			 userService.deleteUser(userId);
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.USER_DELETED,  null);

		
	}
	// getUserbyadmin
	//getUsernameList()
	//login()
	
	

}
