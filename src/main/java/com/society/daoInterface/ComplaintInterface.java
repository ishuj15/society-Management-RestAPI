package com.society.daoInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Complaint;

public interface ComplaintInterface {
	public boolean addComplaint(Complaint complaint) throws SQLException, ClassNotFoundException;
	public List<Complaint> getComplaintsByuserId(String userId) throws SQLException, ClassNotFoundException;
	public List<Complaint> getAllComplaints() throws SQLException, ClassNotFoundException;
	public boolean deleteComplaint(String complaintId) throws SQLException, ClassNotFoundException;
	public boolean updateComplaint(String complaintId, String columnToUpdate, String newValue)
			throws SQLException, ClassNotFoundException ;
}
