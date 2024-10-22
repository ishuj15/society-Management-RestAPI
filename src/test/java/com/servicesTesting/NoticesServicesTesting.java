package com.servicesTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.society.Model.Notices;
import com.society.dao.implementation.NoticesDAO;
import com.society.exceptions.NoticeNotFoundException;
import com.society.serviceImp.NoticeService;

public class NoticesServicesTesting {

    @Mock
    private NoticesDAO noticeDao;

    @InjectMocks
    private NoticeService noticeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateNotice_Success() throws Exception {
        Notices notice = new Notices();
        when(noticeDao.addNotice(notice)).thenReturn(true);

        noticeService.createNotice(notice);

        verify(noticeDao, times(1)).addNotice(notice);
    }

    @Test
    void testCreateNotice_Failure() throws Exception {
        Notices notice = new Notices();
        when(noticeDao.addNotice(notice)).thenReturn(false);

        assertThrows(NoticeNotFoundException.class, () -> noticeService.createNotice(notice));

        verify(noticeDao, times(1)).addNotice(notice);
    }

    @Test
    void testGetAllNotices_Success() throws Exception {
        List<Notices> noticesList = Arrays.asList(new Notices(), new Notices());
        when(noticeDao.getAllNotices()).thenReturn(noticesList);

        List<Notices> result = noticeService.getAllNotices();

        assertEquals(noticesList, result);
        verify(noticeDao, times(1)).getAllNotices();
    }

    @Test
    void testGetAllNotices_EmptyList() throws Exception {
        when(noticeDao.getAllNotices()).thenReturn(Collections.emptyList());

        assertThrows(NoticeNotFoundException.class, () -> noticeService.getAllNotices());

        verify(noticeDao, times(1)).getAllNotices();
    }

    @Test
    void testGetNoticesByRole_Success() throws Exception {
        List<Notices> noticesList = Arrays.asList(new Notices(), new Notices());
        when(noticeDao.getNoticeByRole("admin")).thenReturn(noticesList);

        List<Notices> result = noticeService.getNoticeByRole("admin");

        assertEquals(noticesList, result);
        verify(noticeDao, times(1)).getNoticeByRole("admin");
    }

    @Test
    void testGetNoticesByRole_Failure() throws Exception {
        when(noticeDao.getNoticeByRole("unknown_role")).thenReturn(Collections.emptyList());

        assertThrows(NoticeNotFoundException.class, () -> noticeService.getNoticeByRole("unknown_role"));

        verify(noticeDao, times(1)).getNoticeByRole("unknown_role");
    }

    @Test
    void testGetNoticeById_Success() throws Exception {
        Notices notice = new Notices();
        when(noticeDao.getNoticeByNoticeId("123")).thenReturn(notice);

        Notices result = noticeService.getNoticeByNoticeId("123");

        assertEquals(notice, result);
        verify(noticeDao, times(1)).getNoticeByNoticeId("123");
    }

    @Test
    void testGetNoticeById_NotFound() throws Exception {
        when(noticeDao.getNoticeByNoticeId("999")).thenReturn(null);

        assertThrows(NoticeNotFoundException.class, () -> noticeService.getNoticeByNoticeId("999"));

        verify(noticeDao, times(1)).getNoticeByNoticeId("999");
    }

    @Test
    void testUpdateNotice_Success() throws Exception {
        Notices existingNotice = new Notices();
        Notices updatedNotice = new Notices();
        updatedNotice.setTitle("Updated Title");

        when(noticeDao.getNoticeByNoticeId("123")).thenReturn(existingNotice);
        when(noticeDao.updateNotice("123", "title", updatedNotice.getTitle())).thenReturn(true);

        noticeService.updateNotice("123", updatedNotice);

        verify(noticeDao, times(1)).updateNotice("123", "title", updatedNotice.getTitle());
    }

    @Test
    void testUpdateNotice_NotFound() throws Exception {
        Notices updatedNotice = new Notices();
        when(noticeDao.getNoticeByNoticeId("123")).thenReturn(null);

        assertThrows(NoticeNotFoundException.class, () -> noticeService.updateNotice("123", updatedNotice));

        verify(noticeDao, times(1)).getNoticeByNoticeId("123");
    }

    @Test
    void testDeleteNotice_Success() throws Exception {
        when(noticeDao.deleteNotice("123")).thenReturn(false);

        noticeService.deleteNotice("123");

        verify(noticeDao, times(1)).deleteNotice("123");
    }

    @Test
    void testDeleteNotice_Failure() throws Exception {
        when(noticeDao.deleteNotice("123")).thenReturn(true);

        assertThrows(NoticeNotFoundException.class, () -> noticeService.deleteNotice("123"));

        verify(noticeDao, times(1)).deleteNotice("123");
    }
}

