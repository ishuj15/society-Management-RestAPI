package com.servicesTesting;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.society.Model.Services;
import com.society.dao.implementation.ServicesDAO;
import com.society.exceptions.ServiceException;
import com.society.serviceImp.ServicesService;

public class ServiceTesting {
    @Mock
    private ServicesDAO serviceDao;

    @InjectMocks
    private ServicesService servicesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateService_Success() throws SQLException, ClassNotFoundException {
        Services service = new Services();
        // Set service properties here...

        when(serviceDao.addService(service)).thenReturn(true);

        assertDoesNotThrow(() -> servicesService.createService(service));
    }

    @Test
    public void testCreateService_Failure() throws SQLException, ClassNotFoundException {
        Services service = new Services();
        // Set service properties here...

        when(serviceDao.addService(service)).thenReturn(false);

        assertThrows(ServiceException.class, () -> servicesService.createService(service));
    }

    @Test
    public void testRetrieveAllServices_Success() throws SQLException, ClassNotFoundException {
        List<Services> servicesList = Collections.singletonList(new Services(/* set properties */));
        when(serviceDao.getAllServices()).thenReturn(servicesList);

        List<Services> result = servicesService.retriveAllServices();
        assertEquals(servicesList.size(), result.size());
    }

    @Test
    public void testRetrieveAllServices_Failure() throws SQLException, ClassNotFoundException {
        when(serviceDao.getAllServices()).thenReturn(Collections.emptyList());

        assertThrows(ServiceException.class, () -> servicesService.retriveAllServices());
    }

    @Test
    public void testRetrieveServiceByUser_Success() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        List<Services> servicesList = Collections.singletonList(new Services(/* set properties */));
        when(serviceDao.getServiceByUserId(userId)).thenReturn(servicesList);

        List<Services> result = servicesService.retriveServiceByUser(userId);
        assertEquals(servicesList.size(), result.size());
    }

    @Test
    public void testRetrieveServiceByUser_Failure() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        when(serviceDao.getServiceByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(ServiceException.class, () -> servicesService.retriveServiceByUser(userId));
    }

    @Test
    public void testDeleteServiceById_Success() throws SQLException, ClassNotFoundException {
        String serviceId = "service1";

        when(serviceDao.deleteService(serviceId)).thenReturn(true);

        assertDoesNotThrow(() -> servicesService.deleteServiceById(serviceId));
    }

    @Test
    public void testDeleteServiceById_Failure() throws SQLException, ClassNotFoundException {
        String serviceId = "service1";

        when(serviceDao.deleteService(serviceId)).thenReturn(false);

        assertThrows(ServiceException.class, () -> servicesService.deleteServiceById(serviceId));
    }
}

