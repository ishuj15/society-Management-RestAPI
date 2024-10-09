package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.Notices;
import com.society.daoInterface.NoticeInterface;
@Repository
public class NoticesDAO extends GenericDAO<Notices> implements NoticeInterface {

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

	public boolean addNotice(Notices notice) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
				"INSERT INTO notices (idNotices, title, message, date, targetRole) VALUES ('%s','%s','%s','%s','%s')",
				notice.getIdNotices(), notice.getTitle(), notice.getMessage(), notice.getDate(),
				notice.getTargetRole());
		return executeQuery(sqlQuery);
	}

	public List<Notices> getNoticeByRole(String role) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("SELECT * FROM notices WHERE targetRole = '%s' OR targetRole = 'all'",role);

		return executeGetAllQuery(sqlQuery);
	}

	public List<Notices> getAllNotices() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM notices";
		return executeGetAllQuery(sqlQuery);
	}

	public boolean deleteNotice(String noticeId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM notices WHERE idNotices = \"" + noticeId + "\"";
		return executeQuery(sqlQuery);
	}

	public boolean updateNotice(String userId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("UPDATE notices SET %s = '%s' WHERE idNotices = '%s'", columnToUpdate, newValue,
				userId);
		return executeQuery(sqlQuery);
	}
}
