package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Services;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.ServicesDAO;
import com.society.exceptions.ServiceException;
import com.society.serviceInterface.ServicesServiceInterface;

@Service
public class ServicesService implements ServicesServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ServicesService.class);

    @Autowired
    public ServicesDAO serviceDao;

    @Override
    public boolean createService(Services service) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Creating service: {}", service);
            if (!serviceDao.addService(service)) {
                logger.error("Failed to create service: {}", service);
                throw new ServiceException(ApiMessages.UNABLE_TO_CREATE_SERVICE);
            }
            logger.info("Service created successfully: {}", service);
            return true;
        } catch (Exception e) {
            logger.error("Error while creating service: {}", service, e);
            throw e;
        }
    }

    @Override
    public List<Services> retriveAllServices() throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving all services");
            List<Services> list = serviceDao.getAllServices();
            if (list.isEmpty()) {
                logger.warn("No services found");
                throw new ServiceException(ApiMessages.SERVICE_NOT_FOUND);
            }
            logger.info("Retrieved {} services", list.size());
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving all services", e);
            throw e;
        }
    }

    @Override
    public List<Services> retriveServiceByUser(String userId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Retrieving services for user ID: {}", userId);
            List<Services> list = serviceDao.getServiceByUserId(userId);
            if (list.isEmpty()) {
                logger.warn("No services found for user ID: {}", userId);
                throw new ServiceException(ApiMessages.SERVICE_NOT_FOUND);
            }
            logger.info("Retrieved {} services for user ID: {}", list.size(), userId);
            return list;
        } catch (Exception e) {
            logger.error("Error while retrieving services for user ID: {}", userId, e);
            throw e;
        }
    }

    @Override
    public boolean updateService() throws ClassNotFoundException, SQLException {
        logger.info("Update service functionality is not implemented yet");
        // Placeholder for future implementation
        return true;
    }

    @Override
    public boolean deleteServiceById(String serviceId) throws ClassNotFoundException, SQLException {
        try {
            logger.info("Deleting service with ID: {}", serviceId);
            if (!serviceDao.deleteService(serviceId)) {
                logger.error("Failed to delete service with ID: {}", serviceId);
                throw new ServiceException(ApiMessages.UNABLE_TO_DELETE_SERVICE);
            }
            logger.info("Service deleted successfully with ID: {}", serviceId);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting service with ID: {}", serviceId, e);
            throw e;
        }
    }
}
