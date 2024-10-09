package com.society.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.Complaint;
import com.society.dao.implementation.ComplaintDAO;

@Service
public class ComplaintService 
{
	public ComplaintDAO complaintDao= new ComplaintDAO();
	public void createComplaint(Complaint complaint) throws ClassNotFoundException, SQLException {
		complaintDao.addComplaint(complaint);
	}
	
	public List<Complaint> retriveAllComplaint() throws ClassNotFoundException, SQLException {
		return complaintDao.getAllComplaints();	
	}
	public List<Complaint> retriveComplaintByUser(String userId)throws ClassNotFoundException, SQLException  {
		return complaintDao.getComplaintById(userId);
	}
	
	public List<String> updateComplaint(Complaint complaint) throws ClassNotFoundException, SQLException  {
		List<String> list =new ArrayList<>();
		if(complaint.getStatus()!=null)
		{
			complaintDao.updateComplaint(complaint.getIdComplaint(), "status", complaint.getStatus());
			list.add("Status updated completely");
		}
		else
		{
			list.add("Could not able to update");
		}
		return list;
	}
	public void deleteComplaint(String complaintId) throws ClassNotFoundException, SQLException {
		complaintDao.deleteComplaint(complaintId);
	}

}
