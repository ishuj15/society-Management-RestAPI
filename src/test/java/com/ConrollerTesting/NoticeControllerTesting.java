package com.ConrollerTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.society.Model.Notices;
import com.society.constants.ApiMessages;
import com.society.controller.NoticesController;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.NoticeService;

public class NoticeControllerTesting {
	@Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private NoticesController noticesController;

    @Mock
    private NoticeService noticeService;

    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(noticesController).build();

    }

    // Utility function to convert objects to JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //1.Test case for successful creation of a notice
    @Test
    void testCreateNotice_Success() throws Exception {
        Notices notice = new Notices();
        notice.setIdNotices("123");
        doNothing().when(noticeService).createNotice(any(Notices.class));

        ResponseEntity<Object> response = noticesController.createNotice(notice);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiMessages.NOTICE_CREATED, ((ApiResponseHandler) response.getBody()).getMessage());
    }
    
    //2.Test case for failure in creating a notice (SQLException)
    @Test
    void testCreateNotice_Failure() throws Exception {
        Notices notice = new Notices();
        doThrow(new SQLException()).when(noticeService).createNotice(any(Notices.class));

        ResponseEntity<Object> response = noticesController.createNotice(notice);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    
    //3. Test case for retrieving all notices successfully
    @Test
    void testRetrieveAllNotices_Success() throws Exception {
        List<Notices> noticesList = Arrays.asList(new Notices(), new Notices());
        when(noticeService.getAllNotices()).thenReturn(noticesList);

        ResponseEntity<Object> response = noticesController.retriveAllNotices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiMessages.FETCHED, ((ApiResponseHandler) response.getBody()).getMessage());
        assertEquals(noticesList, ((ApiResponseHandler) response.getBody()).getData());
    }

    //4.Test case for failure in retrieving all notices (SQLException)
    @Test
    void testRetrieveAllNotices_Failure() throws Exception {
        doThrow(new SQLException()).when(noticeService).getAllNotices();

        ResponseEntity<Object> response = noticesController.retriveAllNotices();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //5. Test case for retrieving notices by role successfully
    @Test
    void testRetrieveNoticeByRole_Success() throws Exception {
        List<Notices> noticesList = Arrays.asList(new Notices(), new Notices());
        when(noticeService.getNoticeByRole("admin")).thenReturn(noticesList);

        ResponseEntity<Object> response = noticesController.retriveNoticeByRole("admin");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiMessages.FETCHED, ((ApiResponseHandler) response.getBody()).getMessage());
        assertEquals(noticesList, ((ApiResponseHandler) response.getBody()).getData());
    }

    //6. Test case for failure in retrieving notices by role (role not found)
    @Test
    void testRetrieveNoticeByRole_Failure() throws Exception {
        doThrow(new SQLException()).when(noticeService).getNoticeByRole("unknown_role");

        ResponseEntity<Object> response = noticesController.retriveNoticeByRole("unknown_role");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //7. Test case for retrieving a notice by noticeId successfully
    @Test
    void testGetNoticeByNoticeId_Success() throws Exception {
        Notices notice = new Notices();
        when(noticeService.getNoticeByNoticeId("123")).thenReturn(notice);

        ResponseEntity<Object> response = noticesController.getNoticeByNoticeId("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notice, ((ApiResponseHandler) response.getBody()).getData());
    }

    //8. Test case for retrieving a notice by noticeId (notice not found)
    @Test
    void testGetNoticeByNoticeId_NotFound() throws Exception {
        when(noticeService.getNoticeByNoticeId("999")).thenReturn(null);

        ResponseEntity<Object> response = noticesController.getNoticeByNoticeId("999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    //9. Test case for updating a notice successfully
    @Test
    void testUpdateNotice_Success() throws Exception {
        Notices updatedNotice = new Notices();
        doNothing().when(noticeService).updateNotice("123", updatedNotice);

        ResponseEntity<Object> response = noticesController.updateNotice("123", updatedNotice);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiMessages.NOTICE_UPDATED, ((ApiResponseHandler) response.getBody()).getMessage());
    }

    //10. Test case for failure in updating a notice (notice not found)
    @Test
    void testUpdateNotice_Failure() throws Exception {
        Notices updatedNotice = new Notices();
        doThrow(new SQLException()).when(noticeService).updateNotice("123", updatedNotice);

        ResponseEntity<Object> response = noticesController.updateNotice("123", updatedNotice);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //11. Test case for deleting a notice successfully
    @Test
    void testDeleteNotice_Success() throws Exception {
        doNothing().when(noticeService).deleteNotice("123");

        ResponseEntity<Object> response = noticesController.deleteNotice("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiMessages.NOTICE_DELETED, ((ApiResponseHandler) response.getBody()).getMessage());
    }

    //12. Test case for deleting a notice (notice not found)
    @Test
    void testDeleteNotice_NotFound() throws Exception {
        doThrow(new SQLException()).when(noticeService).deleteNotice("999");

        ResponseEntity<Object> response = noticesController.deleteNotice("999");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    //13. Test case for invalid notice ID during deletion
    @Test
    void testDeleteNotice_InvalidNoticeId() throws Exception {
        ResponseEntity<Object> response = noticesController.deleteNotice("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    //14. Test case for invalid role during notice retrieval
    @Test
    void testRetrieveNoticeByRole_InvalidRole() throws Exception {
        ResponseEntity<Object> response = noticesController.retriveNoticeByRole("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    //15. Test case for creating a notice with invalid data (null message)
    @Test
    void testCreateNotice_InvalidData() throws Exception {
        Notices notice = new Notices();
        notice.setMessage(null); // Invalid data
        
        ResponseEntity<Object> response = noticesController.createNotice(notice);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
