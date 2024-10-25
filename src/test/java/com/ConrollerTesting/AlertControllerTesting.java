package com.ConrollerTesting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import com.society.constants.ApiMessages;
import com.society.controller.AlertController;
import com.society.exceptions.AlertsException;
import com.society.serviceImp.AlertService;
import com.society.serviceInterface.AlertServiceInterface;

public class AlertControllerTesting {
	 private MockMvc mockMvc;

	    @Mock
	    private AlertServiceInterface alertService;

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
	    
	    // 1.Success Test Case for Creating Alert
	    @Test
	    public void testCreateAlertSuccess() throws Exception {

	        Alert alert = new Alert();
	        alert.setMessage("This is a test alert");
	        alert.setIdAlert("1");
	        doNothing().when(alertService).addAlert(alert);
	        mockMvc.perform(post("/alert")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(alert)))
	                .andExpect(status().isCreated());
	        //verify(alertService, times(1)).addAlert(alert);
	    } 
	     
	    // 2.Failure Test Case for Creating Alert
	    @Test
	    public void testCreateAlert_Failure() throws Exception {
	        Alert alert = new Alert(); 
	        alert.setIdAlert("1");
	        
	       doThrow( new AlertsException(ApiMessages.UNABLE_TO_CREATE_ALERT)).when(alertService).addAlert(alert);
	        mockMvc.perform(post("/alert")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(alert)))
	                .andExpect(status().isBadRequest());

	       // verify(alertService, times(1)).addAlert(any(Alert.class));
	    }
	 
	    // 3.Success Test Case for Retrieving All Alerts
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

	       // verify(alertService, times(1)).retriveAllAlerts();
	    }
	
	    //4. Failure Test Case for Retrieving All Alerts
	    @Test
	    public void testRetrieveAllAlerts_Failure() throws Exception {

	        when(alertService.retriveAllAlerts()).thenThrow(new AlertsException("Error fetching alerts"));
	        
	        mockMvc.perform(get("/alerts"))
	                .andExpect(status().isInternalServerError());

	      //  verify(alertService, times(1)).retriveAllAlerts();
	    }
	    
	    // 5.Success Test Case for Retrieving Alerts by Role
	    @Test
	    public void testRetrieveAlertsByRoleSuccess() throws Exception {
	        Alert alert1= new Alert();
	    	alert1.setDate(null);
	    	alert1.setIdAlert("1");
	    	alert1.setMessage("alert 1");
	    	alert1.setTargetRole("all");

	    	List<Alert> alerts = Arrays.asList(alert1);
	        when(alertService.retriveAlertByRole("resident")).thenReturn(alerts);
	        mockMvc.perform(get("/alerts/resident"))
	        .andExpect(status().isOk());
	        //	.andExpect(jsonPath("$.length()").value(1));
//	                .andExpect(jsonPath("$[0].title").value("Alert 1"));
	      //  verify(alertService, times(1)).retriveAlertByRole("resident");
	    }
	    
	 // 6.Failure Test Case for Retrieving Alerts by Role
	    @Test
	    public void testRetrieveAlertsByRole_Failure() throws Exception {
	        when(alertService.retriveAlertByRole("resident")).thenThrow(new AlertsException("Error fetching alerts for resident"));

	        mockMvc.perform(get("/alerts/resident"))
	                .andExpect(status().isBadRequest());

	       // verify(alertService, times(1)).retriveAlertByRole("resident");
	    }
	    @Test
	    public void testUpdateAlertSuccess() throws Exception {
	        Alert alert = new Alert();
	        alert.setIdAlert("1");
	        alert.setMessage("alert");	        
	        doNothing().when( alertService).updateAlert("1",alert);
	        

	        mockMvc.perform(patch("/alert/1")
	                .contentType(MediaType.APPLICATION_JSON))
	              //  .content(asJsonString(alert)))
	                .andExpect(status().isOk());

//	        verify(alertService, times(1)).updateAlert("1",alert);
	    }

	 //7. Success Test Case for Deleting Alert
	    @Test
	    public void testDeleteAlertSuccess() throws Exception {
	        Alert alert = new Alert();
	        alert.setIdAlert("1");
	        doNothing().when(alertService).deleteAlert("1");
	        mockMvc.perform(delete("/alert/1"))
	                .andExpect(status().isOk());

	      //  verify(alertService, times(1)).deleteAlert("1");
	    }
	
	    //8.Failure Test Case for Deleting Alert
	    @Test
		public void testDeleteAlert_Failure() throws Exception {
	    	//Alert alert= new alert();
	    	doThrow(new AlertsException(ApiMessages.UNABLE_TO_DELETE_ALERT)).when(alertService).deleteAlert("1");

		  mockMvc.perform(delete("/alert/1")) 
		            .andExpect(status().isBadRequest());
		
		    //verify(alertService, times(1)).deleteAlert("1");
				}

	    //9.Test for Successful Retrieval of Alerts by Guard Role
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
		
		   // verify(alertService, times(1)).retriveAlertByRole("guard");
		}

		//10.Test for Successful Retrieval of Alerts with No Data:
		@Test
		public void testRetrieveNoAlerts() throws Exception {
		    when(alertService.retriveAllAlerts()).thenReturn(Arrays.asList());

		    mockMvc.perform(get("/alerts"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.length()").value(0));

		  //  verify(alertService, times(1)).retriveAllAlerts();
		}

		//11.Test for Retrieving Specific Alert by ID:
		@Test
		public void testRetrieveAlertById() throws Exception {
		    Alert alert = new Alert();
		    alert.setIdAlert("1");
		    alert.setMessage("Specific Alert");

		    when(alertService.getAlertById("1")).thenReturn(alert);

		    mockMvc.perform(get("/alert/1"))
		            .andExpect(status().isOk());
//		            .andExpect(jsonPath("$.message").value(ApiMessages.FETCHED));

		   // verify(alertService, times(1)).getAlertById("1");
		} 

		//12.Test for Update Alert with Full Data:
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

//		    verify(alertService, times(1)).updateAlert("2",any(Alert.class));
		}
		
		//13.Test for Deleting Non-existent Alert:
		@Test
		public void testDeleteNonExistentAlert() throws Exception {
		    mockMvc.perform(delete("/alert/999"))
		            .andExpect(status().isOk()); // Assuming the service handles non-existent deletes gracefully

//		    verify(alertService, times(1)).deleteAlert("999");
		}
		
		//14.Test for Alert Creation with Empty Data (Failure):
		@Test
		public void testCreateAlertEmptyDataFailure() throws Exception {
		    Alert alert = new Alert(); // Empty object

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isBadRequest());
		}

		//15.Test for Alert Creation with Invalid Role (Failure):
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

		//16.Test for Alert Creation with Invalid JSON (Failure):
		@Test
		public void testCreateAlertInvalidJsonFailure() throws Exception {
		    String invalidJson = "{ \"message\": \"Incomplete JSON\" "; // Invalid JSON format

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(invalidJson))
		            .andExpect(status().isBadRequest());
		}

		//17.Test for Retrieval of Alerts by Invalid Role (Failure):
		@Test
		public void testRetrieveAlertsByInvalidRoleFailure() throws Exception {
		    when(alertService.retriveAlertByRole("invalid")).thenReturn(Arrays.asList());

		    mockMvc.perform(get("/alerts/invalid"))
		            .andExpect(status().isNotFound());
		}

		//18.Test for Update of Non-existent Alert (Failure):
		@Test
		public void testUpdateNonExistentAlertFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage("Non-existent alert");

		    doThrow(new AlertsException("invalid alert Id")). when(alertService).updateAlert(null, any(Alert.class)); // Simulate failure

		    mockMvc.perform(patch("/alert/999")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isNotFound());
		}

		//19.Test for Delete of Invalid Alert ID (Failure):
		@Test
		public void testDeleteInvalidAlertIdFailure() throws Exception {
		    doThrow(new AlertsException("invalid alert Id")).when(alertService).deleteAlert("invalid");

		    mockMvc.perform(delete("/alert/invalid"))
		            .andExpect(status().isNotFound());
		}

		//20.Test for Retrieval of Non-existent Alert by ID (Failure):
		@Test
		public void testRetrieveNonExistentAlertByIdFailure() throws Exception {
		    when(alertService.getAlertById("999")).thenReturn(null);

		    mockMvc.perform(get("/alert/999"))
		            .andExpect(status().isNotFound());
		}

		//21.Test for Creation of Alert with Null Message (Failure):
		@Test
		public void testCreateAlertWithNullMessageFailure() throws Exception {
		    Alert alert = new Alert();
		    alert.setMessage(null); // Null message

		    mockMvc.perform(post("/alert")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(asJsonString(alert)))
		            .andExpect(status().isBadRequest());
		}

//		//22.Test for Alert Update with Missing Required Fields (Failure):
//		@Test
//		public void testUpdateAlertMissingFieldsFailure() throws Exception {
//		    Alert alert = new Alert();
//		    alert.setMessage(null); // Missing required message
//		}
}




