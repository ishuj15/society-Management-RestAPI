package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Complaint;

public interface ComplaintServiceInterface {
	public boolean createComplaint(Complaint complaint) throws SQLException, ClassNotFoundException ;
	public List<Complaint> retriveComplaintByUser(String userId) throws SQLException, ClassNotFoundException ;
	public List<Complaint> retriveAllComplaint() throws SQLException, ClassNotFoundException ;
	public boolean deleteComplaint(String complaintId) throws SQLException, ClassNotFoundException ;
	
//	public Complaint getComplaint() throws ClassNotFoundException, SQLException ;
	public Complaint getComplaintByComplaintId(String complaintId) throws ClassNotFoundException, SQLException;
	public boolean updateComplaint(String complaintId, Complaint complaint) throws ClassNotFoundException, SQLException;
}
