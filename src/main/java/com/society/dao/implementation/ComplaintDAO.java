package com.society.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.society.Model.Complaint;
import com.society.daoInterface.ComplaintInterface;
@Repository
public class ComplaintDAO extends GenericDAO<Complaint> implements ComplaintInterface{

	@Override
	protected Complaint mapResultSetToEntity(ResultSet resultSet) throws SQLException {
		Complaint complaint = new Complaint();
		complaint.setIdComplaint(resultSet.getString("idcomplaint"));
		complaint.setUserId(resultSet.getString("idUser"));
		complaint.setDescription(resultSet.getString("description"));
		complaint.setDate(resultSet.getDate("date"));
		complaint.setStatus(resultSet.getString("status"));

		return complaint;
	}

	public boolean addComplaint(Complaint complaint) throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format(
				"INSERT INTO complaint (idcomplaint, idUser, description, date,status) VALUES ('%s','%s','%s','%s','%s')",
				complaint.getIdComplaint(), complaint.getUserId(), complaint.getDescription(), complaint.getDate(),
				complaint.getStatus());
		return executeQuery(sqlQuery);
	}

	public List<Complaint> getComplaintById(String userId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM complaint WHERE idUser = \"" + userId + "\"";
		return executeGetAllQuery(sqlQuery);
	}

	public List<Complaint> getAllComplaints() throws SQLException, ClassNotFoundException {
		String sqlQuery = "SELECT * FROM complaint";
		return executeGetAllQuery(sqlQuery);
	}

	public boolean deleteComplaint(String complaintId) throws SQLException, ClassNotFoundException {
		String sqlQuery = "DELETE FROM complaint WHERE idcomplaint = \"" + complaintId + "\"";
		return executeQuery(sqlQuery);
	}

	public boolean updateComplaint(String complaintId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException {
		String sqlQuery = String.format("UPDATE complaint SET %s = '%s' WHERE idcomplaint = '%s'", columnToUpdate,
				newValue, complaintId);
		return executeQuery(sqlQuery);
	}
}