package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Complaint;

public interface ComplaintServiceInterface {
	public void createComplaint(Complaint complaint) throws SQLException, ClassNotFoundException ;
	public List<Complaint> retriveComplaintByUser(String userId) throws SQLException, ClassNotFoundException ;
	public List<Complaint> retriveAllComplaint() throws SQLException, ClassNotFoundException ;
	public void deleteComplaint(String complaintId) throws SQLException, ClassNotFoundException ;
	
//	public Complaint getComplaint() throws ClassNotFoundException, SQLException ;
	Complaint getComplaintByComplaintId(String complaintId) throws ClassNotFoundException, SQLException;
	public void updateComplaint(String complaintId, Complaint complaint) throws ClassNotFoundException, SQLException;
}
