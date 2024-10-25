package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Complaint;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ComplaintDAO;
import com.society.exceptions.ComplaintException;
import com.society.serviceInterface.ComplaintServiceInterface;

@Service
public class ComplaintService implements ComplaintServiceInterface
{
	@Autowired
	public ComplaintDAO complaintDao;
	@Override
	public boolean createComplaint(Complaint complaint) throws ClassNotFoundException, SQLException {
		if(!complaintDao.addComplaint(complaint))
			throw new ComplaintException(ApiMessages.COMPLAINT_CREATED);
		return true;

	}
	@Override
	public List<Complaint> retriveAllComplaint() throws ClassNotFoundException, SQLException {
		List<Complaint> list = complaintDao.getAllComplaints();	
		if(list.isEmpty())
		{
			throw new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
		}
		else
			return list;
	}
	@Override
	public List<Complaint> retriveComplaintByUser(String userId)throws ClassNotFoundException, SQLException  {
		List<Complaint> list = complaintDao.getComplaintsByuserId(userId);
		
		if(list.isEmpty())
		{
			throw new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
		}
		else
			return list;
	}
	@Override
	public boolean updateComplaint(String complaintId,Complaint complaint) throws ClassNotFoundException, SQLException  {
		Complaint existingcomplaint =complaintDao.getComplaintByComplaintId(complaint.getIdComplaint());
		if(existingcomplaint==null)
			throw new ComplaintException(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT);

 		if(complaint.getStatus()!=null)
		{
			complaintDao.updateComplaint(complaintId, "status", complaint.getStatus());
		}	
		return true;

	}
	@Override
	public boolean deleteComplaint(String complaintId) throws ClassNotFoundException, SQLException {
		if(!complaintDao.deleteComplaint(complaintId))
			throw new ComplaintException(ApiMessages.COMPLIANT_DELETED);
		return true;

	}
	
	@Override
	public Complaint getComplaintByComplaintId(String complaintId) throws ClassNotFoundException, SQLException {
		Complaint complaint=complaintDao.getComplaintByComplaintId(complaintId);
		if(complaint==null)
			throw new ComplaintException(ApiMessages.COMPLAINT_NOT_FOUND);
		return complaint;
	}
	

}
