package com.ConrollerTesting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import com.society.Model.Alert;
import com.society.controller.AlertController;
import com.society.exceptions.AlertNotFoundException;
import com.society.serviceImp.AlertService;

public class AlertControllerTesting {
	 private MockMvc mockMvc;

	    @Mock
	    private AlertService alertService;

	    @InjectMocks
	    private AlertController alertController;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();
	    }
	    private static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    // Success Test Case for Creating Alert
	    @Test
	    public void testCreateAlertSuccess() throws Exception {

	        Alert alert = new Alert();
	      
	        alert.setMessage("This is a test alert");

	        mockMvc.perform(post("/alert")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(alert)))
	                .andExpect(status().isCreated());

	        verify(alertService, times(1)).addAlert(any(Alert.class));
	    }
	    
	    // Failure Test Case for Creating Alert
	    @Test
	    public void testCreateAlert_Failure() throws Exception {
	        Alert alert = new Alert(); 
	        alert.setIdAlert("1");// Missing required fields
	        when(alertService.addAlert(any(Alert.class))).thenThrow(new RuntimeException("Error while creating alert"));

	        mockMvc.perform(post("/alert")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(alert)))
	                .andExpect(status().isInternalServerError());

	        verify(alertService, times(1)).addAlert(any(Alert.class));
	    }
	 
	    // Success Test Case for Retrieving All Alerts
	    @Test
	    public void testRetrieveAllAlertsSuccess() throws Exception {
	    	Alert alert1= new Alert();
	    	alert1.setDate(null);
	    	alert1.setIdAlert("1");
	    	alert1.setMessage("alert 1");
	    	alert1.setTargetRole("all");
	    	
	    	Alert alert2= new Alert();
	    	alert2.setIdAlert("2");
	    	alert2.setDate(null);
	    	alert2.setMessage("alert 2");
	    	alert2.setTargetRole("guard");
	    	
	        List<Alert> alerts = Arrays.asList(alert1, alert2);
	        
	        when(alertService.retriveAllAlerts()).thenReturn(alerts);

	        mockMvc.perform(get("/alerts"))
	                .andExpect(status().isOk());
	              //  .andExpect(jsonPath("$.length()").value(alerts.size()));
//	                .andExpect(jsonPath("$[0].title").value("Alert 1"))
//	                .andExpect(jsonPath("$[1].title").value("Alert 2"));

	        verify(alertService, times(1)).retriveAllAlerts();
	    }
	
	    // Failure Test Case for Retrieving All Alerts
	    @Test
	    public void testRetrieveAllAlerts_Failure() throws Exception {

	        when(alertService.retriveAllAlerts()).thenThrow(new RuntimeException("Error fetching alerts"));

	        mockMvc.perform(get("/alerts"))
	                .andExpect(status().isInternalServerError());

	        verify(alertService, times(1)).retriveAllAlerts();
	    }
	    
	    // Success Test Case for Retrieving Alerts by Role
	    @Test
	    public void testRetrieveAlertsByRoleSuccess() throws Exception {
	        Alert alert1= new Alert();
	    	alert1.setDate(null);
	    	alert1.setIdAlert("1");
	    	alert1.setMessage("alert 1");
	    	alert1.setTargetRole("all");

	    	List<Alert> alerts = Arrays.asList(alert1);
	        when(alertService.retriveAlertByRole("all")).thenReturn(alerts);

	        mockMvc.perform(get("/alerts/resident"))
	        .andExpect(status().isOk());
	        //	.andExpect(jsonPath("$.length()").value(1));
//	                .andExpect(jsonPath("$[0].title").value("Alert 1"));

	        verify(alertService, times(1)).retriveAlertByRole("resident");
	    }
	    
	 // Failure Test Case for Retrieving Alerts by Role
	    @Test
	    public void testRetrieveAlertsByRole_Failure() throws Exception {
	        when(alertService.retriveAlertByRole("resident")).thenThrow(new RuntimeException("Error fetching alerts for resident"));

	        mockMvc.perform(get("/alerts/resident"))
	                .andExpect(status().isInternalServerError());

	        verify(alertService, times(1)).retriveAlertByRole("resident");
	    }

	    @Test
	    public void testUpdateAlertSuccess() throws Exception {
	        Alert alert = new Alert();

//	        when(alertService.updateAlert(alert.getIdAlert(),alert).thenReturn();

	        mockMvc.perform(patch("/alert/1")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(alert)))
	                .andExpect(status().isOk());

	        verify(alertService, times(1)).updateAlert(alert.getIdAlert(),alert);
	    }

	 // Success Test Case for Deleting Alert
	    @Test
	    public void testDeleteAlertSuccess() throws Exception {
	        mockMvc.perform(delete("/alert/1"))
	                .andExpect(status().isOk());

	        verify(alertService, times(1)).deleteAlert("1");
	    }
	
	    //Failure Test Case for Deleting Alert
	    @Test
		public void testDeleteAlert_Failure() throws Exception {
		    when(alertService.deleteAlert("1")).thenThrow(new RuntimeException("Error deleting alert"));
		
		    mockMvc.perform(delete("/alert/1"))
		            .andExpect(status().isInternalServerError());
		
		    verify(alertService, times(1)).deleteAlert("1");
				}

	    //Test for Successful Retrieval of Alerts by Guard Role
		@Test
		public void testRetrieveAlertsByGuardRole() throws Exception {
		    Alert alert1 = new Alert();
		    alert1.setIdAlert("1");
		    alert1.setMessage("Guard Alert");
		    alert1.setTargetRole("guard");
		
		    List<Alert> alerts = Arrays.asList(alert1);
		    when(alertService.retriveAlertByRole("guard")).thenReturn(alerts);
		
		    mockMvc.perform(get("/alerts/guard"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.length()").value(alerts.size()))
		            .andExpect(jsonPath("$[0].message").value("Guard Alert"));
		
		    verify(alertService, times(1)).retriveAlertByRole("guard");
		}

		//Test for Successful Retrieval of Alerts with No Data:
		@Test
		public void testRetrieveNoAlerts() throws Exception {
		    when(alertService.retriveAllAlerts()).thenReturn(Arrays.asList());

		    mockMvc.perform(get("/alerts"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.length()").value(0));

		    verify(alertService, times(1)).retriveAllAlerts();
		}

		//Test for Retrieving Specific Alert by ID:
		@Test
		public void testRetrieveAlertById() throws Exception {
		    Alert alert = new Alert();
		    alert.setIdAlert("1");
		    alert.setMessage("Specific Alert");

		    when(alertService.getAlertById("1")).thenReturn(alert);

		    mockMvc.perform(get("/alert/1"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.message").value("Specific Alert"));

		    verify(alertService, times(1)).getAlertById("1");
		}

		//Test for Update Alert with Full Data:
		@Test
		public void testUpdateAlertFullData() throws Exception {
		    Alert alert = new Alert();
		    alert.setIdAlert("2");
		    alert.setMessage("Updated Full Data Alert");
		    alert.setTargetRole("resident");

		    mockMvc.perform(patch("/alert/2")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isOk());

		    verify(alertService, times(1)).updateAlert(any(Alert.class));
		}
		
		//Test for Deleting Non-existent Alert:
		@Test
		public void testDeleteNonExistentAlert() throws Exception {
		    mockMvc.perform(delete("/alert/999"))
		            .andExpect(status().isOk()); // Assuming the service handles non-existent deletes gracefully

		    verify(alertService, times(1)).deleteAlert("999");
		}
		
		//Test for Alert Creation with Empty Data (Failure):
		@Test
		public void testCreateAlertEmptyDataFailure() throws Exception {
		    Alert alert = new Alert(); // Empty object

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isBadRequest());
		}

		//Test for Alert Creation with Invalid Role (Failure):
		@Test
		public void testCreateAlertInvalidRoleFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage("Invalid role alert");
		    alert.setTargetRole("invalidRole");

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isBadRequest());
		}

		//Test for Alert Creation with Invalid JSON (Failure):
		@Test
		public void testCreateAlertInvalidJsonFailure() throws Exception {
		    String invalidJson = "{ \"message\": \"Incomplete JSON\" "; // Invalid JSON format

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(invalidJson))
		            .andExpect(status().isBadRequest());
		}

		//Test for Retrieval of Alerts by Invalid Role (Failure):
		@Test
		public void testRetrieveAlertsByInvalidRoleFailure() throws Exception {
		    when(alertService.retriveAlertByRole("invalid")).thenReturn(Arrays.asList());

		    mockMvc.perform(get("/alerts/invalid"))
		            .andExpect(status().isNotFound());
		}

		//Test for Update of Non-existent Alert (Failure):
		@Test
		public void testUpdateNonExistentAlertFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage("Non-existent alert");

		    when(alertService.updateAlert(any(Alert.class))).thenReturn(false); // Simulate failure

		    mockMvc.perform(patch("/alert/999")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isNotFound());
		}

		//Test for Delete of Invalid Alert ID (Failure):
		@Test
		public void testDeleteInvalidAlertIdFailure() throws Exception {
		    doThrow(new AlertNotFoundException("invalid alert Id")).when(alertService).deleteAlert("invalid");

		    mockMvc.perform(delete("/alert/invalid"))
		            .andExpect(status().isNotFound());
		}

		//Test for Retrieval of Non-existent Alert by ID (Failure):
		@Test
		public void testRetrieveNonExistentAlertByIdFailure() throws Exception {
		    when(alertService.getAlertById("999")).thenReturn(null);

		    mockMvc.perform(get("/alert/999"))
		            .andExpect(status().isNotFound());
		}

		//Test for Creation of Alert with Null Message (Failure):
		@Test
		public void testCreateAlertWithNullMessageFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage(null); // Null message

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isBadRequest());
		}

		//Test for Alert Update with Missing Required Fields (Failure):
		@Test
		public void testUpdateAlertMissingFieldsFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage(null); // Missing required message
		}
}




