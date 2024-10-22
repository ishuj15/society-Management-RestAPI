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

import com.society.Model.Complaint;
import com.society.dao.implementation.ComplaintDAO;

@ExtendWith(MockitoExtension.class)
public class CoplaintDaoTesting {

    @InjectMocks
    private ComplaintDAO complaintDAO;

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
    public void testAddComplaintSuccess() throws SQLException, ClassNotFoundException {
        Complaint complaint = new Complaint();
        complaint.setIdComplaint("1");
        complaint.setUserId("user123");
        complaint.setDescription("Test Complaint");
        complaint.setDate(new java.sql.Date(System.currentTimeMillis()));
        complaint.setStatus("pending");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = complaintDAO.addComplaint(complaint);

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddComplaintFailure() throws SQLException, ClassNotFoundException {
        Complaint complaint = new Complaint();
        complaint.setIdComplaint("1");
        complaint.setUserId("user123");
        complaint.setDescription("Test Complaint");
        complaint.setDate(new java.sql.Date(System.currentTimeMillis()));
        complaint.setStatus("pending");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = complaintDAO.addComplaint(complaint);

        assertFalse(result);
               verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetComplaintByIdSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("idcomplaint")).thenReturn("1");
        when(resultSet.getString("idUser")).thenReturn("user123");
        when(resultSet.getString("description")).thenReturn("Test Complaint");
        when(resultSet.getDate("date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("pending");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Complaint> result = complaintDAO.getComplaintsByuserId("user123");

        assertNotNull(result);
        assertEquals(1, result.size());
        Complaint complaint = result.get(0);
        assertEquals("1", complaint.getIdComplaint());
        assertEquals("user123", complaint.getUserId());
        assertEquals("Test Complaint", complaint.getDescription());
        assertEquals("pending", complaint.getStatus());
    }

    @Test
    public void testGetComplaintByIdEmptyResult() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Complaint> result = complaintDAO.getComplaintsByuserId("user123");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

//    @Test
//    public void testGetComplaintByIdFailure() throws SQLException {
//        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//        Exception exception = assertThrows(SQLException.class, () -> {
//            complaintDAO.getComplaintById("user123");
//        });
//
//        assertEquals("Database error", exception.getMessage());
//    }

    @Test
    public void testGetAllComplaintsSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("idcomplaint")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("idUser")).thenReturn("user123").thenReturn("user456");
        when(resultSet.getString("description")).thenReturn("Test Complaint").thenReturn("Another Complaint");
        when(resultSet.getDate("date")).thenReturn(new java.sql.Date(System.currentTimeMillis())).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("pending").thenReturn("resolved");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Complaint> result = complaintDAO.getAllComplaints();

        assertNotNull(result);
        assertEquals(2, result.size());
        Complaint complaint1 = result.get(0);
        assertEquals("1", complaint1.getIdComplaint());
        Complaint complaint2 = result.get(1);
        assertEquals("2", complaint2.getIdComplaint());
    }

    @Test
    public void testGetAllComplaintsFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            complaintDAO.getAllComplaints();
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testUpdateComplaintSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = complaintDAO.updateComplaint("1", "status", "resolved");

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateComplaintFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = complaintDAO.updateComplaint("1", "status", "resolved");

        assertFalse(result);
     verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteComplaintSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = complaintDAO.deleteComplaint("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteComplaintFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = complaintDAO.deleteComplaint("1");

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }
}
