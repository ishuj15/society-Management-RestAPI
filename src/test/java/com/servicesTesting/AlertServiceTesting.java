package com.servicesTesting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.society.Model.Alert;
import com.society.dao.implementation.AlertDAO;
import com.society.exceptions.AlertsException;
import com.society.serviceImp.AlertService;
import com.society.util.Helper;

public class AlertServiceTesting {
    @Mock
    private AlertDAO alertDao;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAlert_Success() throws SQLException, ClassNotFoundException {
        Alert alert = new Alert();
        // Set alert properties here...

        when(alertDao.addAlert(alert)).thenReturn(true);

        assertDoesNotThrow(() -> alertService.addAlert(alert));
    }

    @Test
    public void testAddAlert_Failure() throws SQLException, ClassNotFoundException {
        Alert alert = new Alert();
        // Set alert properties here...

        when(alertDao.addAlert(alert)).thenReturn(false);

        assertThrows(AlertsException.class, () -> alertService.addAlert(alert));
    } 

    @Test
    public void testRetrieveAllAlerts_Success() throws SQLException, ClassNotFoundException {
        List<Alert> alerts = Collections.singletonList(new Alert(/* set properties */));
        when(alertDao.getAllAlerts()).thenReturn(alerts);

        List<Alert> result = alertService.retriveAllAlerts();
        assertEquals(alerts.size(), result.size());
    }

    @Test
    public void testRetrieveAllAlerts_Failure() throws SQLException, ClassNotFoundException {
        when(alertDao.getAllAlerts()).thenReturn(Collections.emptyList());

        assertThrows(AlertsException.class, () -> alertService.retriveAllAlerts());
    }

    @Test
    public void testRetrieveAlertByRole_Success() throws SQLException, ClassNotFoundException {
        String role = "guard";
        List<Alert> alerts = Collections.singletonList(new Alert(/* set properties */));
        when(alertDao.getAlertByRole(role)).thenReturn(alerts);

        List<Alert> result = alertService.retriveAlertByRole(role);
        assertEquals(alerts.size(), result.size());
    }

    @Test
    public void testRetrieveAlertByRole_Failure() throws SQLException, ClassNotFoundException {
        String role = "guard";
        when(alertDao.getAlertByRole(role)).thenReturn(Collections.emptyList());
       // when(Helper.isValidTarget(role)).thenReturn(false);

        assertThrows(AlertsException.class, () -> alertService.retriveAlertByRole(role));
    }

    @Test
    public void testUpdateAlert_Success() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";
        Alert alert = new Alert();
        // Set alert properties here...

        when(alertDao.getAlertById(alertId)).thenReturn(alert);
        when(alertDao.updateAlert(eq(alertId), anyString(), anyString())).thenReturn(true);

        assertDoesNotThrow(() -> alertService.updateAlert(alertId, alert));
    }

    @Test
    public void testUpdateAlert_Failure() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";
        Alert alert = new Alert();
        // Set alert properties here...

        when(alertDao.getAlertById(alertId)).thenReturn(null);

        assertThrows(AlertsException.class, () -> alertService.updateAlert(alertId, alert));
    }

    @Test
    public void testDeleteAlert_Success() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";

        when(alertDao.deleteAlert(alertId)).thenReturn(true);

        assertDoesNotThrow(() -> alertService.deleteAlert(alertId));
    }

    @Test
    public void testDeleteAlert_Failure() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";

        when(alertDao.deleteAlert(alertId)).thenReturn(false);

        assertThrows(AlertsException.class, () -> alertService.deleteAlert(alertId));
    }

    @Test
    public void testGetAlertById_Success() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";
        Alert alert = new Alert();
        // Set alert properties here...

        when(alertDao.getAlertById(alertId)).thenReturn(alert);

        Alert result = alertService.getAlertById(alertId);
        assertEquals(alert, result);
    }

    @Test
    public void testGetAlertById_Failure() throws SQLException, ClassNotFoundException {
        String alertId = "alert1";

        when(alertDao.getAlertById(alertId)).thenReturn(null);

        assertThrows(AlertsException.class, () -> alertService.getAlertById(alertId));
    }
}

