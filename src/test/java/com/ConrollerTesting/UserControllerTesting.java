package com.ConrollerTesting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.society.Model.User;
import com.society.controller.UserController;
import com.society.exceptions.UserNotFoundException;
import com.society.serviceImp.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.jsonwebtoken.lang.Collections;

public class UserControllerTesting {
	@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

    }

    // Utility function to convert objects to JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //1.Test Create User Success
	@Test
	public void testCreateUserSuccess() throws Exception {
	    User user = new User();
	    // set user details
	    when(userService.createUser(any(User.class))).thenReturn(user);
	    mockMvc.perform(post("/api/auth/user")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(user)))
	            .andExpect(status().isOk());
	}
	//2.Test Create User Failure
	@Test
	public void testCreateUserFailure() throws Exception {
	    User user = new User();
	    // set user details
	    doThrow(new SQLException()).when(userService).createUser(any(User.class));
	    mockMvc.perform(post("/api/auth/user")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(user)))
	            .andExpect(status().isInternalServerError());
	}
	//3.Test Retrieve All Users Success
	@Test
	public void testRetrieveAllUsersSuccess() throws Exception {
	    List<User> users = new ArrayList<>();
	    users.add(new User());
	    when(userService.retriveAllUsers()).thenReturn(users);
	    mockMvc.perform(get("/users"))
	            .andExpect(status().isOk());
	}
	//4. Test Retrieve All Users No Users Found
	@Test
	public void testRetrieveAllUsersNoUsers() throws Exception {
	    when(userService.retriveAllUsers()).thenReturn(Collections.emptyList());
	    mockMvc.perform(get("/users"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.data").isEmpty());
	}
	//5.Test Retrieve User By ID Success
	@Test
	public void testRetrieveUserByIdSuccess() throws Exception {
	    User user = new User();
	    when(userService.retriveUserById("1")).thenReturn(user);
	    mockMvc.perform(get("/user/{userId}", "1"))
	            .andExpect(status().isOk());
	}
//	6.Test Retrieve User By ID Not Found
	@Test
	public void testRetrieveUserByIdNotFound() throws Exception {
	    when(userService.retriveUserById("1")).thenThrow(new UserNotFoundException());
	    mockMvc.perform(get("/user/{userId}", "1"))
	            .andExpect(status().isNotFound());
	}
	//7.Test Update User Success
	@Test
	public void testUpdateUserSuccess() throws Exception {
	    User user = new User();
	    mockMvc.perform(patch("/user/{userId}", "1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(user)))
	            .andExpect(status().isOk());
	}
	//8.Test Update User Failure
	@Test
	public void testUpdateUserFailure() throws Exception {
	    doThrow(new SQLException()).when(userService).updateUser(eq("1"), any(User.class));
	    mockMvc.perform(patch("/user/{userId}", "1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(asJsonString(new User())))
	            .andExpect(status().isInternalServerError());
	}
	//9.Test Delete User Success
	@Test
	public void testDeleteUserSuccess() throws Exception {
	    mockMvc.perform(delete("/user/{userId}", "1"))
	            .andExpect(status().isOk());
	}
	//10.Test Delete User Failure
	@Test
	public void testDeleteUserFailure() throws Exception {
	    doThrow(new UserNotFoundException()).when(userService).deleteUser("1");
	    mockMvc.perform(delete("/user/{userId}", "1"))
	            .andExpect(status().isNotFound());
	}

	

}
