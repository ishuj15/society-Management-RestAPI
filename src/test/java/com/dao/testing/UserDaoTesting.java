package com.dao.testing;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.society.Model.User;
import com.society.dao.implementation.UserDAO;

@ExtendWith(MockitoExtension.class)
public class UserDaoTesting {

    @InjectMocks
    private UserDAO userDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testAddUserSuccess() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setIdUser("1");
        user.setUserName("john_doe");
        user.setUserRole("resident");
        user.setPassword("password123");
        user.setPhoneNo("1234567890");
        user.setEmail("john.doe@example.com");
        user.setAddress("123 Main St");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.addUser(user);

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddUserFailure() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setIdUser("1");
        user.setUserName("john_doe");
        user.setUserRole("resident");
        user.setPassword("password123");
        user.setPhoneNo("1234567890");
        user.setEmail("john.doe@example.com");
        user.setAddress("123 Main St");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = userDAO.addUser(user);

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetUserByIdSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("idUser")).thenReturn("1");
        when(resultSet.getString("userName")).thenReturn("john_doe");
        when(resultSet.getString("userRole")).thenReturn("resident");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getString("phoneNo")).thenReturn("1234567890");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getString("address")).thenReturn("123 Main St");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        User result = userDAO.getUserById("1");

        assertNotNull(result);
        assertEquals("1", result.getIdUser());
        assertEquals("john_doe", result.getUserName());
        assertEquals("resident", result.getUserRole());
        assertEquals("password123", result.getPassword());
        assertEquals("1234567890", result.getPhoneNo());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("123 Main St", result.getAddress());
    }

    @Test
    public void testGetUserByIdFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            userDAO.getUserById("1");
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testGetUserByUserNameSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("idUser")).thenReturn("1");
        when(resultSet.getString("userName")).thenReturn("john_doe");
        when(resultSet.getString("userRole")).thenReturn("resident");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getString("phoneNo")).thenReturn("1234567890");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");
        when(resultSet.getString("address")).thenReturn("123 Main St");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        User result = userDAO.getUserByUserName("john_doe");

        assertNotNull(result);
        assertEquals("1", result.getIdUser());
        assertEquals("john_doe", result.getUserName());
        assertEquals("resident", result.getUserRole());
        assertEquals("password123", result.getPassword());
        assertEquals("1234567890", result.getPhoneNo());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("123 Main St", result.getAddress());
    }

    @Test
    public void testGetUserByUserNameFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            userDAO.getUserByUserName("john_doe");
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testDeleteUserSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.deleteUser("1");

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteUserFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = userDAO.deleteUser("1");

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllUsersSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("idUser")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("userName")).thenReturn("john_doe").thenReturn("jane_doe");
        when(resultSet.getString("userRole")).thenReturn("resident").thenReturn("admin");
        when(resultSet.getString("password")).thenReturn("password123").thenReturn("password456");
        when(resultSet.getString("phoneNo")).thenReturn("1234567890").thenReturn("0987654321");
        when(resultSet.getString("email")).thenReturn("john.doe@example.com").thenReturn("jane.doe@example.com");
        when(resultSet.getString("address")).thenReturn("123 Main St").thenReturn("456 Elm St");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<User> result = userDAO.getAllUsers(0,10);

        assertNotNull(result);
        assertEquals(2, result.size());
        User user1 = result.get(0);
        assertEquals("1", user1.getIdUser());
        User user2 = result.get(1);
        assertEquals("2", user2.getIdUser());
    }

    @Test
    public void testGetAllUsersFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            userDAO.getAllUsers(0,5);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testUpdateUserSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDAO.updateUser("1", "userName", "updated_name");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateUserFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = userDAO.updateUser("1", "userName", "updated_name");

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }
}

