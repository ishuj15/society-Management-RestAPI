//package com.ConrollerTesting;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.society.Model.Complaint;
//import com.society.constants.ApiMessages;
//import com.society.controller.ComplaintController;
//import com.society.exceptions.ComplaintException;
//import com.society.serviceImp.ComplaintService;
//
//public class ComplaintControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private ComplaintController complaintController;
//
//    @Mock
//    private ComplaintService complaintService;
//
//    private Complaint complaint;
//    private List<Complaint> complaintList;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(complaintController).build();
//
//        complaint = new Complaint();
//        complaint.setIdComplaint("1");
//        complaint.setDescription("Sample complaint");
//        
//        complaintList = new ArrayList<>();
//        complaintList.add(complaint);
//    }
//
//    // Utility function to convert objects to JSON
//    private String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // Test cases
//
//    // 1. Test create complaint successfully
//    @Test
//    public void createComplaint_success() throws Exception {
//        doNothing().when(complaintService).createComplaint(any(Complaint.class));
//
//        mockMvc.perform(post("/complaint")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(ApiMessages.COMPLAINT_CREATED));
//    }
//
//    // 2. Test create complaint when service throws exception
//    @Test
//    public void createComplaint_failure() throws Exception {
//        doThrow(new ClassNotFoundException()).when(complaintService).createComplaint(any(Complaint.class));
//
//        mockMvc.perform(post("/complaint")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isInternalServerError());
//    }
//
//    // 3. Test retrieve all complaints successfully
//    @Test
//    public void retrieveAllComplaints_success() throws Exception {
//        when(complaintService.retriveAllComplaint()).thenReturn(complaintList);
//
//        mockMvc.perform(get("/complaints"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].idComplaint").value("1"));
//    }
//
//    // 4. Test retrieve all complaints when no data found
//    @Test
//    public void retrieveAllComplaints_empty() throws Exception {
//        when(complaintService.retriveAllComplaint()).thenThrow(new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY));
//
//        mockMvc.perform(get("/complaints"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ApiMessages.NO_COMPLAINT_TO_DISPLAY));
//    }
//
//    // 5. Test retrieve complaints by user ID successfully
//    @Test
//    public void retrieveComplaintsByUser_success() throws Exception {
//        when(complaintService.retriveComplaintByUser("user123")).thenReturn(complaintList);
//
//        mockMvc.perform(get("/complaints/user123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].idComplaint").value("1"));
//    }
//
//    // 6. Test retrieve complaints by user ID when no complaints exist
//    @Test
//    public void retrieveComplaintsByUser_empty() throws Exception {
//        when(complaintService.retriveComplaintByUser("user123")).thenThrow(new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY));
//
//        mockMvc.perform(get("/complaints/user123"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ApiMessages.NO_COMPLAINT_TO_DISPLAY));
//    }
//
//    // 7. Test retrieve complaint by complaint ID successfully
//    @Test
//    public void retrieveComplaintByComplaintId_success() throws Exception {
//        when(complaintService.getComplaintByComplaintId("1")).thenReturn(complaint);
//
//        mockMvc.perform(get("/complaint/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.idComplaint").value("1"));
//    }
//
//    // 8. Test retrieve complaint by complaint ID when complaint is not found
//    @Test
//    public void retrieveComplaintByComplaintId_notFound() throws Exception {
//        when(complaintService.getComplaintByComplaintId("1")).thenThrow(new ComplaintException(ApiMessages.COMPLAINT_NOT_FOUND));
//
//        mockMvc.perform(get("/complaint/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ApiMessages.COMPLAINT_NOT_FOUND));
//    }
//
//    // 9. Test update complaint successfully
//    @Test
//    public void updateComplaint_success() throws Exception {
//        doNothing().when(complaintService).updateComplaint(anyString(), any(Complaint.class));
//
//        mockMvc.perform(patch("/complaint/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(ApiMessages.COMPLAINT_UPDATED));
//    }
//
//    // 10. Test update complaint when complaint ID is not found
//    @Test
//    public void updateComplaint_failure() throws Exception {
//        doThrow(new ComplaintException(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT))
//                .when(complaintService).updateComplaint(anyString(), any(Complaint.class));
//
//        mockMvc.perform(patch("/complaint/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT));
//    }
//
//    // 11. Test delete complaint successfully
//    @Test
//    public void deleteComplaint_success() throws Exception {
//        doNothing().when(complaintService).deleteComplaint("1");
//
//        mockMvc.perform(delete("/complaint/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value(ApiMessages.COMPLIANT_DELETED));
//    }
//
//    // 12. Test delete complaint when complaint ID is not found
//    @Test
//    public void deleteComplaint_failure() throws Exception {
//        doThrow(new ComplaintException(ApiMessages.COMPLIANT_DELETED))
//                .when(complaintService).deleteComplaint("1");
//
//        mockMvc.perform(delete("/complaint/1"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value(ApiMessages.COMPLIANT_DELETED));
//    }
//
//    // Additional tests for bad requests, invalid input, etc.
//    // 13. Test create complaint with invalid input
//    @Test
//    public void createComplaint_invalidInput() throws Exception {
//        complaint.setDescription(null); // invalid input
//
//        mockMvc.perform(post("/complaint")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isBadRequest());
//    }
//
//    // 14. Test retrieve complaints with invalid user ID
//    @Test
//    public void retrieveComplaintsByUser_invalidUserId() throws Exception {
//        mockMvc.perform(get("/complaints/invalidUserId"))
//                .andExpect(status().isBadRequest());
//    }
//
//    // 15. Test update complaint with invalid status field
//    @Test
//    public void updateComplaint_invalidField() throws Exception {
//        complaint.setStatus(null); // invalid status
//
//        mockMvc.perform(patch("/complaint/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isBadRequest());
//    }
//
//    // Continue adding more cases for different scenarios, edge cases, and exceptional flows
//
//    // 16. Test retrieve complaints when SQLException occurs
//    @Test
//    public void retrieveComplaints_sqlException() throws Exception {
//        when(complaintService.retriveAllComplaint()).thenThrow(new SQLException());
//
//        mockMvc.perform(get("/complaints"))
//                .andExpect(status().isInternalServerError());
//    }
//
//    // 17. Test create complaint when SQLException occurs
//    @Test
//    public void createComplaint_sqlException() throws Exception {
//        doThrow(new SQLException()).when(complaintService).createComplaint(any(Complaint.class));
//
//        mockMvc.perform(post("/complaint")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(complaint)))
//                .andExpect(status().isInternalServerError());
//    }
//
//   
//}
