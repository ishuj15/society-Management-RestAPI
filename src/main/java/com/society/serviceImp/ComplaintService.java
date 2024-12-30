package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Complaint;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ComplaintDAO;
import com.society.exceptions.ComplaintException;
import com.society.serviceInterface.ComplaintServiceInterface;

@Service
public class ComplaintService implements ComplaintServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ComplaintService.class);

    @Autowired
    public ComplaintDAO complaintDao;

    @Override
    public boolean createComplaint(Complaint complaint) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Creating complaint: {}", complaint);
            if (!complaintDao.addComplaint(complaint)) {
                logger.error("Failed to create complaint: {}", complaint);
                throw new ComplaintException(ApiMessages.COMPLAINT_CREATED);
            }
            logger.info("Complaint created successfully: {}", complaint);
            return true;
        } catch (Exception e) {
            logger.error("Error while creating complaint: {}", complaint, e);
            throw new ComplaintException("Failed to create complaint");
        }
    }

    @Override
    public List<Complaint> retriveAllComplaint() throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all complaints");
            List<Complaint> list = complaintDao.getAllComplaints();
            if (list.isEmpty()) {
                logger.error("No complaints found");
                throw new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
            }
            logger.info("Retrieved {} complaints", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving all complaints", e);
            throw new ComplaintException("Failed to retrieve complaints");
        }
    }

    @Override
    public List<Complaint> retriveComplaintByUser(String userId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving complaints for user ID: {}", userId);
            List<Complaint> list = complaintDao.getComplaintsByuserId(userId);
            if (list.isEmpty()) {
                logger.error("No complaints found for user ID: {}", userId);
                throw new ComplaintException(ApiMessages.NO_COMPLAINT_TO_DISPLAY);
            }
            logger.info("Retrieved {} complaints for user ID: {}", list.size(), userId);
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving complaints for user ID: {}", userId, e);
            throw new ComplaintException("Failed to retrieve complaints for user");
        }
    }

    @Override
    public boolean updateComplaint(String complaintId, Complaint complaint) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Updating complaint with ID: {}", complaintId);
            Complaint existingComplaint = complaintDao.getComplaintByComplaintId(complaint.getIdComplaint());
            if (existingComplaint == null) {
                logger.error("No complaint found with ID: {}", complaintId);
                throw new ComplaintException(ApiMessages.UNABLE_TO_UPDATE_COMPLAINT);
            }
            if (complaint.getStatus() != null) {
                complaintDao.updateComplaint(complaintId, "status", complaint.getStatus());
            }
            logger.info("Complaint updated successfully with ID: {}", complaintId);
            return true;
        } catch (Exception e) {
            logger.error("Error while updating complaint with ID: {}", complaintId, e);
            throw new ComplaintException("Failed to update complaint");
        }
    }

    @Override
    public boolean deleteComplaint(String complaintId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting complaint with ID: {}", complaintId);
            if (!complaintDao.deleteComplaint(complaintId)) {
                logger.error("Failed to delete complaint with ID: {}", complaintId);
                throw new ComplaintException(ApiMessages.COMPLIANT_DELETED);
            }
            logger.info("Complaint deleted successfully with ID: {}", complaintId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting complaint with ID: {}", complaintId, e);
            throw new ComplaintException("Failed to delete complaint");
        }
    }

    @Override
    public Complaint getComplaintByComplaintId(String complaintId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving complaint by ID: {}", complaintId);
            Complaint complaint = complaintDao.getComplaintByComplaintId(complaintId);
            if (complaint == null) {
                logger.error("No complaint found with ID: {}", complaintId);
                throw new ComplaintException(ApiMessages.COMPLAINT_NOT_FOUND);
            }
            logger.info("Complaint retrieved successfully: {}", complaint);
            return complaint;
        } catch (Exception e) {
            logger.error("Error while retrieving complaint by ID: {}", complaintId, e);
            throw new ComplaintException("Failed to retrieve complaint by ID");
        }
    }
}
