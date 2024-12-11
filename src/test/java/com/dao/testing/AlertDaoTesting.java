//package com.dao.testing;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.society.Model.Alert;
//import com.society.dao.implementation.AlertDAO;
//
//@ExtendWith(MockitoExtension.class)
//public class AlertDaoTesting {
//
//    @InjectMocks
//    private AlertDAO alertDAO;
//
//    @Mock
//    private Connection connection;
//    @Mock
//    private PreparedStatement preparedStatement;
//
//    @Mock
//    private ResultSet resultSet;
//
//    @BeforeEach
//    public void setUp() throws SQLException {
//        MockitoAnnotations.openMocks(this);
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//    }
//    @Test
//    public void testAddAlert() throws SQLException, ClassNotFoundException {
//        Alert alert = new Alert();
//        alert.setIdAlert("1");
//        alert.setMessage("Test Alert");
//        alert.setDate(new java.sql.Date(System.currentTimeMillis()));
//        alert.setTargetRole("all");
//
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = alertDAO.addAlert(alert);
//
//        assertTrue(result);
//
//        verify(preparedStatement,times(1)).executeUpdate();
//    }
//    @Test
//    public void testAddAlertFailure() throws SQLException, ClassNotFoundException {
//        Alert alert = new Alert();
//        alert.setIdAlert("1");
//        alert.setMessage("Test Alert");
//        alert.setDate(new java.sql.Date(System.currentTimeMillis()));
//        alert.setTargetRole("all");
//
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = alertDAO.addAlert(alert);
//
//        assertFalse(result);
//    }
//
//    @Test
//    public void testGetAlertByRole() throws SQLException, ClassNotFoundException {
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getString("idAlert")).thenReturn("1");
//        when(resultSet.getString("message")).thenReturn("Test Alert");
//        when(resultSet.getDate("date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
//        when(resultSet.getString("targetRole")).thenReturn("all");
//
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Alert> result = alertDAO.getAlertByRole("all");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("1", result.get(0).getIdAlert());
//        assertEquals("Test Alert", result.get(0).getMessage());
//        assertEquals("all", result.get(0).getTargetRole());
//    }
//    @Test
//    public void testGetAlertByRoleEmptyResult() throws SQLException, ClassNotFoundException {
//        when(resultSet.next()).thenReturn(false);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Alert> result = alertDAO.getAlertByRole("all");
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//
//    @Test
//    public void testGetAllAlertsSuccess() throws SQLException, ClassNotFoundException {
//        List<Alert> alertList = new ArrayList<>();
//        Alert alert1 = new Alert();
//        alert1.setIdAlert("1");
//        alert1.setMessage("Alert 1");
//        alert1.setDate(new java.sql.Date(System.currentTimeMillis()));
//        alert1.setTargetRole("all");
//
//        Alert alert2 = new Alert();
//        alert2.setIdAlert("2");
//        alert2.setMessage("Alert 2");
//        alert2.setDate(new java.sql.Date(System.currentTimeMillis()));
//        alert2.setTargetRole("guard");
//
//        alertList.add(alert1);
//        alertList.add(alert2);
//
//        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//        when(resultSet.getString("idAlert")).thenReturn("1").thenReturn("2");
//        when(resultSet.getString("message")).thenReturn("Alert 1").thenReturn("Alert 2");
//        when(resultSet.getDate("date")).thenReturn(alert1.getDate()).thenReturn(alert2.getDate());
//        when(resultSet.getString("targetRole")).thenReturn("all").thenReturn("guard");
//
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Alert> result = alertDAO.getAllAlerts();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("1", result.get(0).getIdAlert());
//        assertEquals("2", result.get(1).getIdAlert());
//    }
////    @Test
////    public void testGetAllAlertsFailure() throws SQLException {
////        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
////
////        Exception exception = assertThrows(SQLException.class, () -> {
////            alertDAO.getAllAlerts();
////        });
////
////        assertEquals("Database error", exception.getMessage());
////    } 
//
//    @Test
//    public void testUpdateAlertSuccess() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = alertDAO.updateAlert("1", "message", "Updated Message");
//
//        assertTrue(result);
//
//    }
//
//    @Test
//    public void testDeleteAlertSuccess() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = alertDAO.deleteAlert("1");
//
//        assertTrue(result);
//
//    }
//    @Test
//    public void testUpdateAlertFailure() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = alertDAO.updateAlert("1", "message", "Updated Message");
//
//        assertFalse(result);
//
//        verify(preparedStatement).executeUpdate();
//    }
//   
//    @Test
//    public void testDeleteAlertFailure() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = alertDAO.deleteAlert("1");
//
//        assertFalse(result);
//
//        verify(preparedStatement).executeUpdate();
//    }
//}
//
//
