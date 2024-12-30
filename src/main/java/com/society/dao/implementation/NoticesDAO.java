package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.society.Model.Notices;
import com.society.daoInterface.NoticeInterface;

@Repository
public class NoticesDAO extends GenericDAO<Notices> implements NoticeInterface {

    private static final Logger logger = LoggerFactory.getLogger(NoticesDAO.class);

    @Override
    protected Notices mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Notices notice = new Notices();
        notice.setIdNotices(resultSet.getString("idNotices"));
        notice.setTitle(resultSet.getString("title"));
        notice.setMessage(resultSet.getString("message"));
        notice.setDate(resultSet.getString("date"));
        notice.setTargetRole(resultSet.getString("targetRole"));
        return notice;
    }

    public boolean addNotice(Notices notice) {
        String sqlQuery = String.format(
                "INSERT INTO notices (idNotices, title, message, date, targetRole) VALUES ('%s','%s','%s','%s','%s')",
                notice.getIdNotices(), notice.getTitle(), notice.getMessage(), notice.getDate(), notice.getTargetRole());
        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error adding notice: {}", e.getMessage(), e);
            return false;
        }
    }

    public List<Notices> getNoticeByRole(String role) {
        String sqlQuery = String.format("SELECT * FROM notices WHERE targetRole = '%s' OR targetRole = 'all'", role);
        try {
            return executeGetAllQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching notices for role {}: {}", role, e.getMessage(), e);
            return null;
        }
    }

    public List<Notices> getAllNotices() {
        String sqlQuery = "SELECT * FROM notices";
        try {
            return executeGetAllQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching all notices: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean deleteNotice(String noticeId) {
        String sqlQuery = "DELETE FROM notices WHERE idNotices = \"" + noticeId + "\"";
        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error deleting notice by ID {}: {}", noticeId, e.getMessage(), e);
            return false;
        }
    }

    public Notices getNoticeByNoticeId(String noticeId) {
        String sqlQuery = "SELECT * FROM notices WHERE idNotices = \"" + noticeId + "\"";
        try {
            return executeGetQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error fetching notice by ID {}: {}", noticeId, e.getMessage(), e);
            return null;
        }
    }

    public boolean updateNotice(String userId, String columnToUpdate, String newValue) {
        String sqlQuery = String.format("UPDATE notices SET %s = '%s' WHERE idNotices = '%s'", columnToUpdate, newValue, userId);
        try {
            return executeQuery(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Error updating notice by ID {}: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}
