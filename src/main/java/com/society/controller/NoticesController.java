package com.society.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.ApiResponseStatus;
import com.society.Model.Notices;
import com.society.constants.ApiMessages;
import com.society.dto.ApiResponseHandler;
import com.society.serviceImp.NoticeService;
import com.society.util.Helper;
import com.society.util.str;

import jakarta.validation.Valid;
@RestController
public class NoticesController {
	@Autowired
	public NoticeService noticeService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); 

	@PostMapping(path="/notice")
	public ResponseEntity<Object> createNotice(@Valid @RequestBody Notices notice) throws ClassNotFoundException, SQLException {
        logger.info("Request to create notice: {}", notice);
 
		try {
			notice.setIdNotices(Helper.generateUniqueId());
			noticeService.createNotice(notice);
			logger.info("notice created successfully: {}", notice.getIdNotices());

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_CREATED, notice);

		} catch (ClassNotFoundException e) {
			 logger.error("Unable to create notice: {}", notice);
				throw new ClassNotFoundException(str.unableToCreate);
		} catch (SQLException e) {
			logger.error("Unable to create notice: {}", notice);
			throw new SQLException(str.unableToCreate);
		}
	}
	
	@GetMapping(path="/notices")
	public ResponseEntity<Object>  retriveAllNotices() throws ClassNotFoundException, SQLException{
		
		logger.info("Request to retrieve  notice : {}");

		try {
			List<Notices>	list = noticeService.getAllNotices();
			 logger.info("notice retrieved successfully: ");
			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive notice ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive notice");
			throw new SQLException(str.unableToRetrive);
		}
	}
	
	@GetMapping(path="/notices/{role}")
	public ResponseEntity<Object> retriveNoticeByRole(@PathVariable String role)throws ClassNotFoundException, SQLException {
		logger.info("Request to retrieve  notice by role: {}");

		List<Notices> list;
		
		try {
			list = noticeService.getNoticeByRole(role);
			 logger.info("notice retrieved successfully: ");
			 return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);

		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive notice ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive notice");
			throw new SQLException(str.unableToRetrive);
		}
	}
	
	@GetMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> getNoticeByNoticeId(@PathVariable String noticeId) throws ClassNotFoundException, SQLException{
		logger.info("Request to retrieve  notice by id: {}");

		try {
			Notices notice=noticeService.getNoticeByNoticeId(noticeId);
			 logger.info("notice retrieved successfully: ");

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_DELETED,  notice);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to retrive notice ");
			throw new ClassNotFoundException(str.unableToRetrive);
		} catch (SQLException e) {
			logger.warn("Not able to retrive notice");
			throw new SQLException(str.unableToRetrive);
		}
	}
	
	@PatchMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> updateNotice(@PathVariable String noticeId , @RequestBody Notices notice) throws ClassNotFoundException, SQLException{
		logger.info("Request to update notice by ID: {}", noticeId);

		try {
			noticeService.updateNotice(noticeId,notice);
            logger.info("notice updated successfully: {}", noticeId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_UPDATED,  null);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to update notice ");
			throw new ClassNotFoundException(str.unableToUpdate);
		} catch (SQLException e) {
			logger.warn("Not able to update  notice ");
			throw new SQLException(str.unableToUpdate);
		}
	}
	
	@DeleteMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> deleteNotice(@PathVariable String noticeId) throws ClassNotFoundException, SQLException{
		logger.info("Request to notice complaint by ID: {}", noticeId);

		try {
			noticeService.deleteNotice(noticeId);
            logger.info("notice deleted successfully: {}", noticeId);

			return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_DELETED,  null);
		} catch (ClassNotFoundException e) {
			logger.warn("Not able to delete notice ");
			throw new ClassNotFoundException(str.unableToDelete);
		} catch (SQLException e) {
			logger.warn("Not able to delete notice ",noticeId);
			throw new SQLException(str.unableToDelete);
		}
	}
}