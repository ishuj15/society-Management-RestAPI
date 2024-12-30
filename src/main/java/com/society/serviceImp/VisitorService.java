package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.User;
import com.society.Model.Visitor;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.VisitorDAO;
import com.society.exceptions.UserException;
import com.society.exceptions.VisitorException;
import com.society.serviceInterface.VisitorServiceInterface;
import com.society.util.Helper;
import com.society.util.str;

@Service
public class VisitorService implements VisitorServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(VisitorService.class);

    @Autowired
    public VisitorDAO visitorDao;

    @Autowired
    public UserService userService;

    @Override
    public boolean createVisitor(Visitor visitor) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Creating visitor: {}", visitor);

            User user = userService.retriveUserById(visitor.getUserId());
            if (user == null) {
                logger.error("User not found for ID: {}", visitor.getUserId());
                throw new UserException(ApiMessages.USER_NOT_FOUND);
            }

            visitor.setIdVisitor(Helper.generateUniqueId());
            visitor.setStatus(user.getUserRole().equals(str.resident) ? str.approved : str.pending);

            if (!visitorDao.addVisitor(visitor)) {
                logger.error("Unable to create visitor: {}", visitor);
                throw new VisitorException(ApiMessages.UNABLE_TO_CREATE_VISITOR);
            }

            logger.info("Visitor created successfully: {}", visitor);
            return true;
        } catch (Exception e) {
            logger.error("Error while creating visitor: {}", visitor, e);
            throw e;
        }
    }

    @Override
    public List<Visitor> retriveAllVisitors() throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all visitors");
            List<Visitor> list = visitorDao.getAllVisitors();
            if (list.isEmpty()) {
                logger.warn("No visitors found");
                throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
            }
            logger.info("Retrieved {} visitors", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving all visitors", e);
            throw e;
        }
    }

    @Override
    public List<Visitor> retriveVisitorByUser(String userId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving visitors for user ID: {}", userId);
            List<Visitor> list = visitorDao.getVisitorById(userId);
            if (list.isEmpty()) {
                logger.warn("No visitors found for user ID: {}", userId);
                throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
            }
            logger.info("Retrieved {} visitors for user ID: {}", list.size(), userId);
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving visitors for user ID: {}", userId, e);
            throw e;
        }
    }

    @Override
    public boolean updateVisitor() throws ClassNotFoundException, SQLException {
        logger.info("Update visitor functionality is not implemented yet");
        // Placeholder for future implementation
        return true;
    }

    @Override
    public boolean deleteVisitorById(String visitorId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting visitor with ID: {}", visitorId);
            if (!visitorDao.deleteVisitor(visitorId)) {
                logger.error("Failed to delete visitor with ID: {}", visitorId);
                throw new VisitorException(ApiMessages.UNABLE_TO_DELETE_VISITOR);
            }
            logger.info("Visitor deleted successfully with ID: {}", visitorId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting visitor with ID: {}", visitorId, e);
            throw e;
        }
    }

    @Override
    public List<Visitor> getAllPendingRequests(String userId, String status) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all pending requests for user ID: {} with status: {}", userId, status);
            List<Visitor> list = visitorDao.getAllpendingRequests(userId, status);
            if (list.isEmpty()) {
                logger.warn("No pending requests found for user ID: {} with status: {}", userId, status);
                throw new VisitorException(ApiMessages.VISITOR_NOT_FOUND);
            }
            logger.info("Retrieved {} pending requests for user ID: {}", list.size(), userId);
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving pending requests for user ID: {} with status: {}", userId, status, e);
            throw e;
        }
    }
}
