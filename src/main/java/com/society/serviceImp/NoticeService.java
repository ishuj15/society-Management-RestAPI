package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Notices;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.NoticesDAO;
import com.society.exceptions.NoticeException;
import com.society.serviceInterface.NoticesServiceInterface;

@Service
public class NoticeService implements NoticesServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    public NoticesDAO noticeDao;

    @Override
    public boolean createNotice(Notices notice) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Creating notice: {}", notice);
            if (!noticeDao.addNotice(notice)) {
                logger.error("Failed to create notice: {}", notice);
                throw new NoticeException(ApiMessages.UNABLE_TO_CREATE_NOTICE);
            }
            logger.info("Notice created successfully: {}", notice);
            return true;
        } catch (Exception e) {
            logger.error("Error while creating notice: {}", notice, e);
            throw new NoticeException("Failed to create notice");
        }
    }

    @Override
    public List<Notices> getAllNotices() throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all notices");
            List<Notices> notices = noticeDao.getAllNotices();
            if (notices.isEmpty()) {
                logger.warn("No notices found");
                throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
            }
            logger.info("Retrieved {} notices", notices.size());
            return notices;
        } catch (Exception e) {
            logger.error("Error while retrieving all notices", e);
            throw new NoticeException("Failed to retrieve notices");
        }
    }

    @Override
    public List<Notices> getNoticeByRole(String role) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving notices for role: {}", role);
            List<Notices> notices = noticeDao.getNoticeByRole(role);
            if (notices.isEmpty()) {
                logger.warn("No notices found for role: {}", role);
                throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
            }
            logger.info("Retrieved {} notices for role: {}", notices.size(), role);
            return notices;
        } catch (Exception e) {
            logger.error("Error while retrieving notices for role: {}", role, e);
            throw new NoticeException("Failed to retrieve notices by role");
        }
    }

    @Override
    public Notices getNoticeByNoticeId(String noticeId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving notice by ID: {}", noticeId);
            Notices notice = noticeDao.getNoticeByNoticeId(noticeId);
            if (notice == null) {
                logger.warn("No notice found with ID: {}", noticeId);
                throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
            }
            logger.info("Notice retrieved successfully: {}", notice);
            return notice;
        } catch (Exception e) {
            logger.error("Error while retrieving notice by ID: {}", noticeId, e);
            throw new NoticeException("Failed to retrieve notice by ID");
        }
    }

    @Override
    public boolean updateNotice(String noticeId, Notices notice) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Updating notice with ID: {}", noticeId);
            Notices existingNotice = noticeDao.getNoticeByNoticeId(noticeId);
            if (existingNotice == null) {
                logger.error("No notice found with ID: {}", noticeId);
                throw new NoticeException(ApiMessages.UNABLE_TO_UPDATE_NOTICE);
            }

            if (notice.getDate() != null) {
                noticeDao.updateNotice(noticeId, "date", notice.getDate());
                logger.info("Updated 'date' for notice ID: {}", noticeId);
            }
            if (notice.getMessage() != null) {
                noticeDao.updateNotice(noticeId, "message", notice.getMessage());
                logger.info("Updated 'message' for notice ID: {}", noticeId);
            }
            if (notice.getTargetRole() != null) {
                noticeDao.updateNotice(noticeId, "targetRole", notice.getTargetRole());
                logger.info("Updated 'targetRole' for notice ID: {}", noticeId);
            }
            if (notice.getTitle() != null) {
                noticeDao.updateNotice(noticeId, "title", notice.getTitle());
                logger.info("Updated 'title' for notice ID: {}", noticeId);
            }
            logger.info("Notice updated successfully with ID: {}", noticeId);
            return true;
        } catch (Exception e) {
            logger.error("Error while updating notice with ID: {}", noticeId, e);
            throw new NoticeException("Failed to update notice");
        }
    }

    @Override
    public boolean deleteNotice(String noticeId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting notice with ID: {}", noticeId);
            if (!noticeDao.deleteNotice(noticeId)) {
                logger.error("Failed to delete notice with ID: {}", noticeId);
                throw new NoticeException(ApiMessages.UNABLE_TO_DELETE_NOTICE);
            }
            logger.info("Notice deleted successfully with ID: {}", noticeId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting notice with ID: {}", noticeId, e);
            throw new NoticeException("Failed to delete notice");
        }
    }
}
