package com.servicesTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.society.Model.User;
import com.society.dao.implementation.UserDAO;
import com.society.exceptions.UserNotFoundException;
import com.society.serviceImp.UserService;

public class UserServiceTesting {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    public UserServiceTesting() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testCreateUserSuccess() throws Exception {
	    User user = new User();
	    when(userDAO.addUser(user)).thenReturn(true);
	    userService.createUser(user);
	    verify(userDAO, times(1)).addUser(user);
	}
	@Test(expected = UserNotFoundException.class)
	public void testCreateUserFailure() throws Exception {
	    User user = new User();
	    when(userDAO.addUser(user)).thenReturn(false);
	    userService.createUser(user);
	}
	@Test
	public void testRetrieveAllUsersSuccess() throws Exception {
	    List<User> users = new ArrayList<>();
	    when(userDAO.getAllUsers()).thenReturn(users);
	    List<User> result = userService.retriveAllUsers();
	    assertEquals(users, result);
	}
	@Test(expected = UserNotFoundException.class)
	public void testRetrieveAllUsersNotFound() throws Exception {
	    when(userDAO.getAllUsers()).thenReturn(Collections.emptyList());
	    userService.retriveAllUsers();
	}
	@Test
	public void testRetrieveUserByIdSuccess() throws Exception {
	    User user = new User();
	    when(userDAO.getUserById("1")).thenReturn(user);
	    User result = userService.retriveUserById("1");
	    assertEquals(user, result);
	}
	@Test(expected = UserNotFoundException.class)
	public void testRetrieveUserByIdNotFound() throws Exception {
	    when(userDAO.getUserById("1")).thenReturn(null);
	    userService.retriveUserById("1");
	}
	@Test
	public void testUpdateUserSuccess() throws Exception {
	    User existingUser = new User();
	    when(userDAO.getUserById("1")).thenReturn(existingUser);
	    userService.updateUser("1", new User());
	    verify(userDAO, times(1)).updateUser(anyString(), anyString(), anyString());
	}
	@Test(expected = UserNotFoundException.class)
	public void testUpdateUserNotFound() throws Exception {
	    when(userDAO.getUserById("1")).thenReturn(null);
	    userService.updateUser("1", new User());
	}
	@Test
	public void testDeleteUserSuccess() throws Exception {
	    User user = new User();
	    when(userDAO.getUserById("1")).thenReturn(user);
	    userService.deleteUser("1");
	    verify(userDAO, times(1)).deleteUser("1");
	}
	@Test(expected = UserNotFoundException.class)
	public void testDeleteUserFailure() throws Exception {
	    User adminUser = new User();
	    adminUser.setUserRole("admin");
	    when(userDAO.getUserById("1")).thenReturn(adminUser);
	    userService.deleteUser("1");
	}


}
