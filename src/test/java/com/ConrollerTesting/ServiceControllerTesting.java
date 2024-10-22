package com.ConrollerTesting;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.society.Model.Services;
import com.society.constants.ApiMessages;
import com.society.controller.ServicesController;
import com.society.exceptions.ServiceNotFoundException;
import com.society.serviceImp.ServicesService;

public class ServiceControllerTesting {
    private MockMvc mockMvc;

    @Mock
    private ServicesService servicesService;

    @InjectMocks
    private ServicesController servicesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(servicesController).build();
    }

    @Test
    public void testCreateService_Success() throws Exception {
        Services service = new Services();
        // Set service properties here...

        when(servicesService.createService(any(Services.class))).thenReturn(null); // Assuming the method has a void return type

        mockMvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(service)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value(ApiMessages.SERVICE_CREATED));
    }

    @Test
    public void testCreateService_Failure() throws Exception {
        Services service = new Services();
        // Set service properties here...

        doThrow(new ServiceNotFoundException(ApiMessages.UNABLE_TO_CREATE_SERVICE))
            .when(servicesService).createService(any(Services.class));

        mockMvc.perform(post("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(service)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.message").value(ApiMessages.UNABLE_TO_CREATE_SERVICE));
    }

    @Test
    public void testRetrieveAllServices_Success() throws Exception {
        List<Services> servicesList = List.of(new Services(/* set properties */));

        when(servicesService.retriveAllServices()).thenReturn(servicesList);

        mockMvc.perform(get("/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value(ApiMessages.FETCHED))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(servicesList.size()));
    }

    @Test
    public void testRetrieveAllServices_Failure() throws Exception {
        when(servicesService.retriveAllServices()).thenThrow(new ServiceNotFoundException(ApiMessages.SERVICE_NOT_FOUND));

        mockMvc.perform(get("/services"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.message").value(ApiMessages.SERVICE_NOT_FOUND));
    }

    @Test
    public void testRetrieveServiceByUser_Success() throws Exception {
        String userId = "user1";
        List<Services> servicesList = List.of(new Services(/* set properties */));

        when(servicesService.retriveServiceByUser(userId)).thenReturn(servicesList);

        mockMvc.perform(get("/services/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value(ApiMessages.FETCHED))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(servicesList.size()));
    }

    @Test
    public void testRetrieveServiceByUser_Failure() throws Exception {
        String userId = "user1";
        when(servicesService.retriveServiceByUser(userId)).thenThrow(new ServiceNotFoundException(ApiMessages.SERVICE_NOT_FOUND));

        mockMvc.perform(get("/services/{userId}", userId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.message").value(ApiMessages.SERVICE_NOT_FOUND));
    }

    @Test
    public void testUpdateService_Success() throws Exception {
        String serviceId = "service1";

        // Assuming updateService doesn't require a body yet.
        mockMvc.perform(patch("/service/{serviceId}", serviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value(ApiMessages.SERVICE_UPDATED));
    }

    @Test
    public void testDeleteServiceById_Success() throws Exception {
        String serviceId = "service1";

        doNothing().when(servicesService).deleteServiceById(serviceId);

        mockMvc.perform(delete("/service/{serviceId}", serviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value(ApiMessages.SERVICE_DELETED));
    }

    @Test
    public void testDeleteServiceById_Failure() throws Exception {
        String serviceId = "service1";

        doThrow(new ServiceNotFoundException(ApiMessages.UNABLE_TO_DELETE_SERVICE))
            .when(servicesService).deleteServiceById(serviceId);

        mockMvc.perform(delete("/service/{serviceId}", serviceId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.message").value(ApiMessages.UNABLE_TO_DELETE_SERVICE));
    }
}

