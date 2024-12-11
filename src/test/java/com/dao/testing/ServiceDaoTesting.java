//package com.dao.testing;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
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
//import com.society.Model.Services;
//import com.society.dao.implementation.ServicesDAO;
//
//@ExtendWith(MockitoExtension.class)
//public class ServiceDaoTesting {
//
//    @InjectMocks
//    private ServicesDAO servicesDAO;
//
//    @Mock
//    private Connection connection;
//
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
//
//    @Test
//    public void testAddServiceSuccess() throws SQLException, ClassNotFoundException {
//        Services service = new Services();
//        service.setIdServices("1");
//        service.setUserId("123");
//        service.setServiceName("Cleaning");
//        service.setDescription("Daily cleaning service");
//        service.setStatus("Active");
//
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = servicesDAO.addService(service);
//
//        assertTrue(result);
//       verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testAddServiceFailure() throws SQLException, ClassNotFoundException {
//        Services service = new Services();
//        service.setIdServices("1");
//        service.setUserId("123");
//        service.setServiceName("Cleaning");
//        service.setDescription("Daily cleaning service");
//        service.setStatus("Active");
//
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = servicesDAO.addService(service);
//
//        assertFalse(result);
//       verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testGetServiceByIdSuccess() throws SQLException, ClassNotFoundException {
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getString("idservices")).thenReturn("1");
//        when(resultSet.getString("userId")).thenReturn("123");
//        when(resultSet.getString("serviceName")).thenReturn("Cleaning");
//        when(resultSet.getString("Description")).thenReturn("Daily cleaning service");
//        when(resultSet.getString("status")).thenReturn("Active");
//
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Services> result = servicesDAO.getServiceByUserId("123");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        Services service = result.get(0);
//        assertEquals("1", service.getIdServices());
//        assertEquals("123", service.getUserId());
//        assertEquals("Cleaning", service.getServiceName());
//        assertEquals("Daily cleaning service", service.getDescription());
//        assertEquals("Active", service.getStatus());
//    }
//
//    @Test
//    public void testGetServiceByIdEmptyResult() throws SQLException, ClassNotFoundException {
//        when(resultSet.next()).thenReturn(false);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Services> result = servicesDAO.getServiceByUserId("123");
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testGetServiceByIdFailure() throws SQLException {
//        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//        Exception exception = assertThrows(SQLException.class, () -> {
//            servicesDAO.getServiceByUserId("123");
//        });
//
//        assertEquals("Database error", exception.getMessage());
//    }
//
//    @Test
//    public void testGetAllServicesSuccess() throws SQLException, ClassNotFoundException {
//        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
//
//        when(resultSet.getString("idservices")).thenReturn("1").thenReturn("2");
//        when(resultSet.getString("userId")).thenReturn("123").thenReturn("456");
//        when(resultSet.getString("serviceName")).thenReturn("Cleaning").thenReturn("Gardening");
//        when(resultSet.getString("Description")).thenReturn("Daily cleaning service").thenReturn("Weekly gardening service");
//        when(resultSet.getString("status")).thenReturn("Active").thenReturn("Inactive");
//
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//
//        List<Services> result = servicesDAO.getAllServices();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        Services service1 = result.get(0);
//        assertEquals("1", service1.getIdServices());
//        Services service2 = result.get(1);
//        assertEquals("2", service2.getIdServices());
//    }
//
//    @Test
//    public void testGetAllServicesFailure() throws SQLException {
//        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));
//
//        Exception exception = assertThrows(SQLException.class, () -> {
//            servicesDAO.getAllServices();
//        });
//
//        assertEquals("Database error", exception.getMessage());
//    }
//
//    @Test
//    public void testUpdateServiceSuccess() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = servicesDAO.updateService("1", "serviceName", "Updated Service");
//
//        assertTrue(result);
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testUpdateServiceFailure() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = servicesDAO.updateService("1", "serviceName", "Updated Service");
//
//        assertFalse(result);
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testDeleteServiceSuccess() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(1);
//
//        boolean result = servicesDAO.deleteService("1");
//
//        assertTrue(result);
//        verify(preparedStatement).executeUpdate();
//    }
//
//    @Test
//    public void testDeleteServiceFailure() throws SQLException, ClassNotFoundException {
//        when(preparedStatement.executeUpdate()).thenReturn(0);
//
//        boolean result = servicesDAO.deleteService("1");
//
//        assertFalse(result);
//        verify(preparedStatement).executeUpdate();
//    }
//}
