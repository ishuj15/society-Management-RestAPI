//package com.servicesTesting;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.society.Model.User;
//import com.society.constants.ApiMessages;
//import com.society.dao.implementation.UserDAO;
//import com.society.exceptions.AlertsException;
//import com.society.exceptions.UserException;
//import com.society.serviceImp.UserService;
//
//public class UserServiceTesting {
//
//    @Mock
//    private UserDAO userDAO;
//
//    @InjectMocks
//    private UserService userService;
//
//   
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//       
//    }
//	
//	@Test
//	public void testCreateUserSuccess() throws Exception {
//		 User user = new User();
//		    user.setIdUser("1");
//		    user.setUserName("ishu");
//		    user.setUserRole("resident");
//	    when(userDAO.addUser(user)).thenReturn(true);
//	    assertDoesNotThrow(() ->userService.createUser(user));
//	    verify(userDAO, times(1)).addUser(any(User.class));
//	}
//	@Test( )
//	public void testCreateUserFailure() throws Exception {
//	    User user = new User();
//	    when(userDAO.addUser(user)).thenReturn(false);
//        assertThrows(UserException.class, () ->   userService.createUser(user));
//	}
//	@Test
//	public void testRetrieveAllUsersSuccess() throws Exception {
//		 User user1 = new User();
//		    user1.setIdUser("12");
//		    user1.setUserName("ishuj");
//		    user1.setUserRole("resident");
//			 User user= new User();
//
//		    user.setIdUser("1");
//		    user.setUserName("ishu");
//		    user.setUserRole("resident");
//		  
//	    List<User> users = new ArrayList<>();
//	    users.add(user);
//	    users.add(user1);
//
//	    when(userDAO.getAllUsers(0,5)).thenReturn(users);
//	    List<User> result = userService.retriveAllUsers(0,5);
//	    assertEquals(users, result);
//	}
//	@Test()
//	public void testRetrieveAllUsersNotFound() throws Exception {
//	    when(userDAO.getAllUsers(0,5)).thenReturn(Collections.emptyList());
//	    userService.retriveAllUsers(0,5);
//	}
//	@Test
//	public void testRetrieveUserByIdSuccess() throws Exception {
//	    User user = new User();
//	    when(userDAO.getUserById("1")).thenReturn(user);
//	    User result = userService.retriveUserById("1");
//	    assertEquals(user, result);
//	}
//	@Test()
//	public void testRetrieveUserByIdNotFound() throws Exception {
//	    when(userDAO.getUserById("1")).thenReturn(null);
//        assertThrows(UserException.class, () ->   userService.retriveUserById("1"));
//  
//	}
//	@Test
//	public void testUpdateUserSuccess() throws Exception {
//	    User existingUser = new User();
//	    existingUser.setIdUser("1");
//	    existingUser.setUserName("ishuj");
//	    existingUser.setUserRole("resident");
//	    existingUser.setEmail("email");
//	    
//	    User user = new User();
//		 user.setIdUser("1");
//
//	    when(userDAO.getUserById("1")).thenReturn(existingUser);
//	    
//	   assertDoesNotThrow(() ->userService.updateUser("1",user));
// 
//	  // assertDoesNotThrow( () ->userService.updateUser("1",user ));
//	   // verify(userDAO, times(1)).updateUser("1","email","Email.com");
//	}
//	@Test()
//	public void testUpdateUserNotFound() throws Exception {
//	    when(userDAO.getUserById("1")).thenReturn(null);
//        assertThrows(UserException.class, () ->   userService.updateUser("1", new User()));
//	}
//	@Test
//	public void testDeleteUserSuccess() throws Exception {
//		 User user = new User();
//		 user.setIdUser("1");
//		 user.setUserRole("resident");
//	    when(userDAO.getUserById("1")).thenReturn(user);
//	    userService.deleteUser("1");
//	    verify(userDAO, times(1)).deleteUser("1");
//	}
//	@Test()
//	public void testDeleteUserFailure() throws Exception {
//	    User adminUser = new User();
//	    adminUser.setIdUser("1");
//	    adminUser.setUserRole("admin");
//	    when(userDAO.getUserById("1")).thenReturn(adminUser);
//
//	   assertThrows( UserException.class, () -> userService.deleteUser("1"));
//	}
//
// 
//}
