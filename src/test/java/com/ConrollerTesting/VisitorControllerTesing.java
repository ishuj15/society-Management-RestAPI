//package com.ConrollerTesting;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.society.Model.Visitor;
//import com.society.constants.ApiMessages;
//import com.society.controller.VisitorController;
//import com.society.exceptions.VisitorException;
//import com.society.serviceImp.VisitorService;
//
//public class VisitorControllerTesing {
//    private MockMvc mockMvc;
//
//    @Mock
//    private VisitorService visitorService;
//
//    @InjectMocks
//    private VisitorController visitorController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(visitorController).build();
//    }
//
//    @Test
//    public void testCreateVisitor_Success() throws Exception {
//        Visitor visitor = new Visitor();
//        // Set visitor properties here...
//
//        when(visitorService.createVisitor(visitor)).thenReturn();
//
//        mockMvc.perform(post("/visitor")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(visitor)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.VISITOR_CREATED));
//    }
//
//    @Test
//    public void testCreateVisitor_Failure() throws Exception {
//        Visitor visitor = new Visitor();
//        // Set visitor properties here...
//
//        doThrow(new VisitorException(ApiMessages.UNABLE_TO_CREATE_VISITOR))
//            .when(visitorService).createVisitor(visitor);
//
//        mockMvc.perform(post("/visitor")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(visitor)))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.status").value("FAILURE"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.UNABLE_TO_CREATE_VISITOR));
//    }
//
//    @Test
//    public void testRetrieveAllVisitors_Success() throws Exception {
//        List<Visitor> visitors = List.of(new Visitor(/* set properties */));
//
//        when(visitorService.retriveAllVisitors()).thenReturn(visitors);
//
//        mockMvc.perform(get("/visitors"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.FETCHED))
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data.length()").value(visitors.size()));
//    }
//
//    @Test
//    public void testRetrieveAllVisitors_Failure() throws Exception {
//        when(visitorService.retriveAllVisitors()).thenThrow(new VisitorException(ApiMessages.VISITOR_NOT_FOUND));
//
//        mockMvc.perform(get("/visitors"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.status").value("FAILURE"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.VISITOR_NOT_FOUND));
//    }
//
//    @Test
//    public void testRetrieveVisitorByUser_Success() throws Exception {
//        String userId = "user1";
//        List<Visitor> visitors = List.of(new Visitor(/* set properties */));
//
//        when(visitorService.retriveVisitorByUser(userId)).thenReturn(visitors);
//
//        mockMvc.perform(get("/visitors/{userId}", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.FETCHED))
//                .andExpect(jsonPath("$.data").isArray())
//                .andExpect(jsonPath("$.data.length()").value(visitors.size()));
//    }
//
//    @Test
//    public void testRetrieveVisitorByUser_Failure() throws Exception {
//        String userId = "user1";
//        when(visitorService.retriveVisitorByUser(userId)).thenThrow(new VisitorException(ApiMessages.VISITOR_NOT_FOUND));
//
//        mockMvc.perform(get("/visitors/{userId}", userId))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.status").value("FAILURE"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.VISITOR_NOT_FOUND));
//    }
//
//    @Test
//    public void testDeleteVisitorById_Success() throws Exception {
//        String visitorId = "visitor1";
//
//        doNothing().when(visitorService).deleteVisitorById(visitorId);
//
//        mockMvc.perform(delete("/visitor/{visitorId}", visitorId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.VISITOR_DELETED));
//    }
//
//    @Test
//    public void testDeleteVisitorById_Failure() throws Exception {
//        String visitorId = "visitor1";
//
//        doThrow(new VisitorException(ApiMessages.UNABLE_TO_DELETE_VISITOR)).when(visitorService).deleteVisitorById(visitorId);
//
//        mockMvc.perform(delete("/visitor/{visitorId}", visitorId))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.status").value("FAILURE"))
//                .andExpect(jsonPath("$.message").value(ApiMessages.UNABLE_TO_DELETE_VISITOR));
//    }
//}
//
