package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Complaint;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ComplaintDAO;
import com.society.exceptions.ComplaintNotFoundException;
import com.society.serviceInterface.ComplaintServiceInterface;

@Service
public class ComplaintService implements ComplaintServiceInterface
{
	@Autowired
	public ComplaintDAO complaintDao;
	@Override
	public void createComplaint(Complaint complaint) throws ClassNotFoundException, SQLException {
		if(!complaintDao.addComplaint(complaint))
			throw new ComplaintNotFoundException(ApiMessages.COMPLAINT_CREATED);
	}
	@Override
	public List<Complaint> retriveAllComplaint() throws ClassNotFoundException, SQLException {
		List<Complaint> list = complaintDao.getAllComplaints();	
		if(list.isEmpty())
		{
			throw new ComplaintNotFoundException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
		}
		else
			return list;
	}
	@Override
	public List<Complaint> retriveComplaintByUser(String userId)throws ClassNotFoundException, SQLException  {
		List<Complaint> list = complaintDao.getComplaintsByuserId(userId);
		
		if(list.isEmpty())
		{
			throw new ComplaintNotFoundException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
		}
		else
			return list;
	}
	@Override
	public void updateComplaint(String complaintId,Complaint complaint) throws ClassNotFoundException, SQLException  {
		Complaint existingcomplaint =complaintDao.getComplaintByComplaintId(complaint.getIdComplaint());
		if(existingcomplaint==null)
			throw new ComplaintNotFoundException(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT);

 		if(complaint.getStatus()!=null)
		{
			complaintDao.updateComplaint(complaintId, "status", complaint.getStatus());
		}	
	}
	@Override
	public void deleteComplaint(String complaintId) throws ClassNotFoundException, SQLException {
		if(!complaintDao.deleteComplaint(complaintId))
			throw new ComplaintNotFoundException(ApiMessages.COMPLIANT_DELETED);
	}
	
	@Override
	public Complaint getComplaintByComplaintId(String complaintId) throws ClassNotFoundException, SQLException {
		Complaint complaint=complaintDao.getComplaintByComplaintId(complaintId);
		if(complaint==null)
			throw new ComplaintNotFoundException(ApiMessages.COMPLAINT_NOT_FOUND);
		return complaint;
	}
	

}
