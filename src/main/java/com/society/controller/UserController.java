package com.society.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.User;
import com.society.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	private UserService userService= new UserService();
	 
	public UserController(UserService userService)
	{
		this.userService=userService;
	}	
	@PostMapping(path="/user")
	public void createUser( @Valid @RequestBody  User user) throws ClassNotFoundException, SQLException {
		userService.createUser(user);
	}
	@GetMapping(path="/users")
	public List<User>  retriveAllUsers() throws ClassNotFoundException, SQLException {
		return userService.retriveAllUsers() ;
		
	}
	@GetMapping(path="/user/{id}")
	public User retriveUserById(@PathVariable String id) throws ClassNotFoundException, SQLException {
		return userService.retriveUserById(id);
		
	}
	@PatchMapping(path="/user/{userId}")
	public ArrayList<String> updateUser(@PathVariable String userId, @RequestBody User user) throws ClassNotFoundException, SQLException {
		user.setIdUser(userId);
		return userService.updateUser(user);
		
	}
	@DeleteMapping(path="/user/{userId}")
	public String deleteUser(@PathVariable String userId) throws ClassNotFoundException, SQLException {
			return userService.deleteUser(userId);
		
	}
	// getUserbyadmin
	//getUsernameList()
	//login()
	
	

}
