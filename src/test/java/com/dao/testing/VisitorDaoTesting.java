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

import com.society.Model.Visitor;
import com.society.dao.implementation.VisitorDAO;

@ExtendWith(MockitoExtension.class)
public class VisitorDaoTesting {

    @InjectMocks
    private VisitorDAO visitorDAO;

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
    public void testAddVisitorSuccess() throws SQLException, ClassNotFoundException {
        Visitor visitor = new Visitor();
        visitor.setIdVisitor("1");
        visitor.setUserId("123");
        visitor.setName("John Doe");
        visitor.setArrivalDate("2024-09-12");
        visitor.setPurpose("Meeting");
        visitor.setArrivalTime("10:00:00");
        visitor.setDep_date("2024-09-12");
        visitor.setDepartureTime("11:00:00");
        visitor.setContactNo("9876543210");
        visitor.setStatus("pending");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = visitorDAO.addVisitor(visitor);

        assertTrue(result);
         verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddVisitorFailure() throws SQLException, ClassNotFoundException {
        Visitor visitor = new Visitor();
        visitor.setIdVisitor("1");
        visitor.setUserId("123");
        visitor.setName("John Doe");
        visitor.setArrivalDate("2024-09-12");
        visitor.setPurpose("Meeting");
        visitor.setArrivalTime("10:00:00");
        visitor.setDep_date("2024-09-12");
        visitor.setDepartureTime("11:00:00");
        visitor.setContactNo("9876543210");
        visitor.setStatus("pending");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = visitorDAO.addVisitor(visitor);

        assertFalse(result);
         verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetVisitorByIdSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("idvisitor")).thenReturn("1");
        when(resultSet.getString("userId")).thenReturn("123");
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("date_of_arrival")).thenReturn("2024-09-12");
        when(resultSet.getString("purpose")).thenReturn("Meeting");
        when(resultSet.getString("arrivalTime")).thenReturn("10:00:00");
        when(resultSet.getString("departure_date")).thenReturn("2024-09-12");
        when(resultSet.getString("departureTime")).thenReturn("11:00:00");
        when(resultSet.getString("contact")).thenReturn("9876543210");
        when(resultSet.getString("approvalReq")).thenReturn("pending");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Visitor> result = visitorDAO.getVisitorById("123");

        assertNotNull(result);
        assertEquals(1, result.size());
        Visitor visitor = result.get(0);
        assertEquals("1", visitor.getIdVisitor());
        assertEquals("123", visitor.getUserId());
        assertEquals("John Doe", visitor.getName());
        assertEquals("2024-09-12", visitor.getArrivalDate());
        assertEquals("Meeting", visitor.getPurpose());
        assertEquals("10:00:00", visitor.getArrivalTime());
        assertEquals("2024-09-12", visitor.getDep_date());
        assertEquals("11:00:00", visitor.getDepartureTime());
        assertEquals("9876543210", visitor.getContactNo());
        assertEquals("pending", visitor.getStatus());
    }

    @Test
    public void testGetVisitorByIdEmptyResult() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Visitor> result = visitorDAO.getVisitorById("123");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetVisitorByIdFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            visitorDAO.getVisitorById("123");
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testGetAllVisitorsSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("idvisitor")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("userId")).thenReturn("123").thenReturn("456");
        when(resultSet.getString("name")).thenReturn("John Doe").thenReturn("Jane Smith");
        when(resultSet.getString("date_of_arrival")).thenReturn("2024-09-12").thenReturn("2024-09-13");
        when(resultSet.getString("purpose")).thenReturn("Meeting").thenReturn("Visit");
        when(resultSet.getString("arrivalTime")).thenReturn("10:00:00").thenReturn("11:00:00");
        when(resultSet.getString("departure_date")).thenReturn("2024-09-12").thenReturn("2024-09-13");
        when(resultSet.getString("departureTime")).thenReturn("11:00:00").thenReturn("12:00:00");
        when(resultSet.getString("contact")).thenReturn("9876543210").thenReturn("1234567890");
        when(resultSet.getString("approvalReq")).thenReturn("pending").thenReturn("approved");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Visitor> result = visitorDAO.getAllVisitors();

        assertNotNull(result);
        assertEquals(2, result.size());
        Visitor visitor1 = result.get(0);
        assertEquals("1", visitor1.getIdVisitor());
        Visitor visitor2 = result.get(1);
        assertEquals("2", visitor2.getIdVisitor());
    }

    @Test
    public void testGetAllVisitorsFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            visitorDAO.getAllVisitors();
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testUpdateVisitorSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = visitorDAO.updateVisitor("1", "purpose", "Updated Purpose");

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateVisitorFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = visitorDAO.updateVisitor("1", "purpose", "Updated Purpose");

        assertFalse(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteVisitorSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = visitorDAO.deleteVisitor("1");

        assertTrue(result);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteVisitorFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = visitorDAO.deleteVisitor("1");

        assertFalse(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllPendingRequestsSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("idvisitor")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("userId")).thenReturn("123").thenReturn("456");
        when(resultSet.getString("name")).thenReturn("John Doe").thenReturn("Jane Smith");
        when(resultSet.getString("date_of_arrival")).thenReturn("2024-09-12").thenReturn("2024-09-13");
        when(resultSet.getString("purpose")).thenReturn("Meeting").thenReturn("Visit");
        when(resultSet.getString("arrivalTime")).thenReturn("10:00:00").thenReturn("11:00:00");
        when(resultSet.getString("departure_date")).thenReturn("2024-09-12").thenReturn("2024-09-13");
        when(resultSet.getString("departureTime")).thenReturn("11:00:00").thenReturn("12:00:00");
        when(resultSet.getString("contact")).thenReturn("9876543210").thenReturn("1234567890");
        when(resultSet.getString("approvalReq")).thenReturn("pending").thenReturn("pending");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Visitor> result = visitorDAO.getAllpendingRequests("123", "pending");

        assertNotNull(result);
        assertEquals(2, result.size());
        Visitor visitor1 = result.get(0);
        assertEquals("1", visitor1.getIdVisitor());
        Visitor visitor2 = result.get(1);
        assertEquals("2", visitor2.getIdVisitor());
    }

    @Test
    public void testGetAllPendingRequestsFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> {
            visitorDAO.getAllpendingRequests("123", "pending");
        });

        assertEquals("Database error", exception.getMessage());
    }
}


