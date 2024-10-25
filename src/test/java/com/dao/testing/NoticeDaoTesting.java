package com.dao.testing;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.society.Model.Notices;
import com.society.dao.implementation.NoticesDAO;

@ExtendWith(MockitoExtension.class)
public class NoticeDaoTesting {

    @InjectMocks
    private NoticesDAO noticesDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    public void testAddNoticeSuccess() throws SQLException, ClassNotFoundException {
        Notices notice = new Notices();
        notice.setIdNotices("1");
        notice.setTitle("Notice Title");
        notice.setMessage("Notice Message");
        notice.setDate("2024-09-12");
        notice.setTargetRole("resident");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = noticesDAO.addNotice(notice);

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddNoticeFailure() throws SQLException, ClassNotFoundException {
        Notices notice = new Notices();
        notice.setIdNotices("1");
        notice.setTitle("Notice Title");
        notice.setMessage("Notice Message");
        notice.setDate("2024-09-12");
        notice.setTargetRole("resident");

        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = noticesDAO.addNotice(notice);

        assertFalse(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetNoticeByRoleSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("idNotices")).thenReturn("1");
        when(resultSet.getString("title")).thenReturn("Notice Title");
        when(resultSet.getString("message")).thenReturn("Notice Message");
        when(resultSet.getString("date")).thenReturn("2024-09-12");
        when(resultSet.getString("targetRole")).thenReturn("resident");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notices> result = noticesDAO.getNoticeByRole("resident");

        assertNotNull(result);
        assertEquals(1, result.size());
        Notices notice = result.get(0);
        assertEquals("1", notice.getIdNotices());
        assertEquals("Notice Title", notice.getTitle());
        assertEquals("Notice Message", notice.getMessage());
        assertEquals("2024-09-12", notice.getDate());
        assertEquals("resident", notice.getTargetRole());
    }

    @Test
    public void testGetNoticeByRoleEmptyResult() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(false);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notices> result = noticesDAO.getNoticeByRole("resident");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetNoticeByRoleFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () -> noticesDAO.getNoticeByRole("resident"));

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testGetAllNoticesSuccess() throws SQLException, ClassNotFoundException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("idNotices")).thenReturn("1").thenReturn("2");
        when(resultSet.getString("title")).thenReturn("Notice Title").thenReturn("Another Notice");
        when(resultSet.getString("message")).thenReturn("Notice Message").thenReturn("Another Message");
        when(resultSet.getString("date")).thenReturn("2024-09-12").thenReturn("2024-09-13");
        when(resultSet.getString("targetRole")).thenReturn("resident").thenReturn("guard");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Notices> result = noticesDAO.getAllNotices();

        assertNotNull(result);
        assertEquals(2, result.size());
        Notices notice1 = result.get(0);
        assertEquals("1", notice1.getIdNotices());
        Notices notice2 = result.get(1);
        assertEquals("2", notice2.getIdNotices());
    }

    @Test
    public void testGetAllNoticesFailure() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        Exception exception = assertThrows(SQLException.class, () ->   noticesDAO.getAllNotices()  );

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    public void testUpdateNoticeSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = noticesDAO.updateNotice("1", "title", "Updated Title");

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testUpdateNoticeFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        boolean result = noticesDAO.updateNotice("1", "title", "Updated Title");

        assertFalse(result);
      verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteNoticeSuccess() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = noticesDAO.deleteNotice("1");

        assertTrue(result);
       verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testDeleteNoticeFailure() throws SQLException, ClassNotFoundException {
        when(preparedStatement.executeUpdate()).thenReturn(0);
        boolean result = noticesDAO.deleteNotice("1");

        assertFalse(result);
        verify(preparedStatement).executeUpdate();
    }
    //@Test
//    public void testGetNoticeByIdSuccess() throws SQLException, ClassNotFoundException {
//    	when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getString("idNotices")).thenReturn("1");
//        when(resultSet.getString("title")).thenReturn("Notice Title");
//        when(resultSet.getString("message")).thenReturn("Notice Message");
//        when(resultSet.getString("date")).thenReturn("2024-09-12");
//        when(resultSet.getString("targetRole")).thenReturn("resident");
//
//        when(preparedStatement.executeGetQuery("1")).thenReturn(resultSet);
//
//        noticesDAO.getNoticeByNoticeId("1");
// 
////        assertFalse(result);
//      verify(preparedStatement).executeUpdate();
//    }
}

