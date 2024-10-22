package com.servicesTesting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.society.Model.Complaint;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ComplaintDAO;
import com.society.exceptions.ComplaintNotFoundException;
import com.society.serviceImp.ComplaintService;

public class ComplaintServiceTesting {

    @InjectMocks
    private ComplaintService complaintService;

    @Mock
    private ComplaintDAO complaintDao;

    private Complaint complaint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock complaint object
        complaint = new Complaint();
        complaint.setIdComplaint("1");
        complaint.setDescription("Sample complaint");
    }

    // 1. Test createComplaint method successfully
    @Test
    public void testCreateComplaint_success() throws ClassNotFoundException, SQLException {
        // Mock DAO to return true on adding complaint
        when(complaintDao.addComplaint(any(Complaint.class))).thenReturn(true);

        assertDoesNotThrow(() -> complaintService.createComplaint(complaint));

        verify(complaintDao, times(1)).addComplaint(complaint);
    }

    // 2. Test createComplaint when it throws an exception
    @Test
    public void testCreateComplaint_failure() throws ClassNotFoundException, SQLException {
        // Mock DAO to return false when adding complaint
        when(complaintDao.addComplaint(any(Complaint.class))).thenReturn(false);

        Exception exception = assertThrows(ComplaintNotFoundException.class, () -> {
            complaintService.createComplaint(complaint);
        });

        assertEquals(ApiMessages.COMPLAINT_CREATED, exception.getMessage());
        verify(complaintDao, times(1)).addComplaint(complaint);
    }

    // 3. Test retrieveAllComplaint method when complaints are available
    @Test
    public void testRetrieveAllComplaints_success() throws ClassNotFoundException, SQLException {
        List<Complaint> complaintList = new ArrayList<>();
        complaintList.add(complaint);

        // Mock DAO to return a list of complaints
        when(complaintDao.getAllComplaints()).thenReturn(complaintList);

        List<Complaint> result = complaintService.retriveAllComplaint();
        assertEquals(1, result.size());
        verify(complaintDao, times(1)).getAllComplaints();
    }

    // 4. Test retrieveAllComplaint when no complaints exist
    @Test
    public void testRetrieveAllComplaints_empty() throws ClassNotFoundException, SQLException {
        // Mock DAO to return an empty list
        when(complaintDao.getAllComplaints()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ComplaintNotFoundException.class, () -> {
            complaintService.retriveAllComplaint();
        });

        assertEquals(ApiMessages.NO_COMPLAINT_TO_DISPLAY, exception.getMessage());
        verify(complaintDao, times(1)).getAllComplaints();
    }

    // 5. Test retrieveComplaintByUser when complaints exist for a user
    @Test
    public void testRetrieveComplaintByUser_success() throws ClassNotFoundException, SQLException {
        List<Complaint> complaintList = new ArrayList<>();
        complaintList.add(complaint);

        // Mock DAO to return a list of complaints for a user
        when(complaintDao.getComplaintsByuserId(anyString())).thenReturn(complaintList);

        List<Complaint> result = complaintService.retriveComplaintByUser("user123");
        assertEquals(1, result.size());
        verify(complaintDao, times(1)).getComplaintsByuserId("user123");
    }

    // 6. Test retrieveComplaintByUser when no complaints exist for a user
    @Test
    public void testRetrieveComplaintByUser_empty() throws ClassNotFoundException, SQLException {
        // Mock DAO to return an empty list for a user
        when(complaintDao.getComplaintsByuserId(anyString())).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(ComplaintNotFoundException.class, () -> {
            complaintService.retriveComplaintByUser("user123");
        });

        assertEquals(ApiMessages.NO_COMPLAINT_TO_DISPLAY, exception.getMessage());
        verify(complaintDao, times(1)).getComplaintsByuserId("user123");
    }

    // 7. Test updateComplaint successfully
    @Test
    public void testUpdateComplaint_success() throws ClassNotFoundException, SQLException {
        // Mock DAO to return existing complaint
        when(complaintDao.getComplaintByComplaintId(anyString())).thenReturn(complaint);

        doNothing().when(complaintDao).updateComplaint(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> complaintService.updateComplaint("1", complaint));
        verify(complaintDao, times(1)).updateComplaint("1", "status", complaint.getStatus());
    }

    // 8. Test updateComplaint when complaint not found
    @Test
    public void testUpdateComplaint_failure() throws ClassNotFoundException, SQLException {
        // Mock DAO to return null, simulating complaint not found
        when(complaintDao.getComplaintByComplaintId(anyString())).thenReturn(null);

        Exception exception = assertThrows(ComplaintNotFoundException.class, () -> {
            complaintService.updateComplaint("1", complaint);
        });

        assertEquals(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT, exception.getMessage());
        verify(complaintDao, times(0)).updateComplaint(anyString(), anyString(), anyString());
    }

    // 9. Test deleteComplaint successfully
    @Test
    public void testDeleteComplaint_success() throws ClassNotFoundException, SQLException {
        // Mock DAO to return true on deletion
        when(complaintDao.deleteComplaint(anyString())).thenReturn(true);

        assertDoesNotThrow(() -> complaintService.deleteComplaint("1"));
        verify(complaintDao, times(1)).deleteComplaint("1");
    }

    // 10. Test deleteComplaint when complaint not found
    @Test
    public void testDeleteComplaint_failure() throws ClassNotFoundException, SQLException {
        // Mock DAO to return false, simulating complaint not found
        when(complaintDao.deleteComplaint(anyString())).thenReturn(false);

        Exception exception = assertThrows(ComplaintNotFoundException.class, () -> {
            complaintService.deleteComplaint("1");
        });

        assertEquals(ApiMessages.COMPLIANT_DELETED, exception.getMessage());
        verify(complaintDao, times(1)).deleteComplaint("1");
    }
}

