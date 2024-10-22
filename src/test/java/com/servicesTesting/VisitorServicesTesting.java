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

import com.society.Model.Visitor;
import com.society.dao.implementation.VisitorDAO;
import com.society.exceptions.VisitorNotFoundException;
import com.society.serviceImp.VisitorService;

public class VisitorServicesTesting {
    @Mock
    private VisitorDAO visitorDao;

    @InjectMocks
    private VisitorService visitorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVisitor_Success() throws SQLException, ClassNotFoundException {
        Visitor visitor = new Visitor();
        // Set visitor properties here...

        when(visitorDao.addVisitor(visitor)).thenReturn(true);
        
        assertDoesNotThrow(() -> visitorService.createVisitor(visitor));
    }

    @Test
    public void testCreateVisitor_Failure() throws SQLException, ClassNotFoundException {
        Visitor visitor = new Visitor();
        // Set visitor properties here...

        when(visitorDao.addVisitor(visitor)).thenReturn(false);

        assertThrows(VisitorNotFoundException.class, () -> visitorService.createVisitor(visitor));
    }

    @Test
    public void testRetrieveAllVisitors_Success() throws SQLException, ClassNotFoundException {
        List<Visitor> visitors = Collections.singletonList(new Visitor(/* set properties */));
        when(visitorDao.getAllVisitors()).thenReturn(visitors);

        List<Visitor> result = visitorService.retriveAllVisitors();
        assertEquals(visitors.size(), result.size());
    }

    @Test
    public void testRetrieveAllVisitors_Failure() throws SQLException, ClassNotFoundException {
        when(visitorDao.getAllVisitors()).thenReturn(Collections.emptyList());

        assertThrows(VisitorNotFoundException.class, () -> visitorService.retriveAllVisitors());
    }

    @Test
    public void testRetrieveVisitorByUser_Success() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        List<Visitor> visitors = Collections.singletonList(new Visitor(/* set properties */));
        when(visitorDao.getVisitorById(userId)).thenReturn(visitors);

        List<Visitor> result = visitorService.retriveVisitorByUser(userId);
        assertEquals(visitors.size(), result.size());
    }

    @Test
    public void testRetrieveVisitorByUser_Failure() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        when(visitorDao.getVisitorById(userId)).thenReturn(Collections.emptyList());

        assertThrows(VisitorNotFoundException.class, () -> visitorService.retriveVisitorByUser(userId));
    }

    @Test
    public void testDeleteVisitorById_Success() throws SQLException, ClassNotFoundException {
        String visitorId = "visitor1";

        when(visitorDao.deleteVisitor(visitorId)).thenReturn(true);

        assertDoesNotThrow(() -> visitorService.deleteVisitorById(visitorId));
    }

    @Test
    public void testDeleteVisitorById_Failure() throws SQLException, ClassNotFoundException {
        String visitorId = "visitor1";

        when(visitorDao.deleteVisitor(visitorId)).thenReturn(false);

        assertThrows(VisitorNotFoundException.class, () -> visitorService.deleteVisitorById(visitorId));
    }

    @Test
    public void testGetAllPendingRequests_Success() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        String status = "pending";
        List<Visitor> visitors = Collections.singletonList(new Visitor(/* set properties */));
        when(visitorDao.getAllpendingRequests(userId, status)).thenReturn(visitors);

        List<Visitor> result = visitorService.getAllPendingRequests(userId, status);
        assertEquals(visitors.size(), result.size());
    }

    @Test
    public void testGetAllPendingRequests_Failure() throws SQLException, ClassNotFoundException {
        String userId = "user1";
        String status = "pending";
        when(visitorDao.getAllpendingRequests(userId, status)).thenReturn(Collections.emptyList());

        assertThrows(VisitorNotFoundException.class, () -> visitorService.getAllPendingRequests(userId, status));
    }
}

