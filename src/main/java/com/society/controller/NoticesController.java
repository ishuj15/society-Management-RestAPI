package com.society.controller;

import java.sql.SQLException;
import java.util.List;

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

import jakarta.validation.Valid;
@RestController
public class NoticesController {
	@Autowired
	public NoticeService noticeService;

	@PostMapping(path="/notice")
	public ResponseEntity<Object> createNotice(@Valid @RequestBody Notices notice) throws ClassNotFoundException, SQLException {
		notice.setIdNotices(Helper.generateUniqueId());
		noticeService.createNotice(notice);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_CREATED, notice);
	}
	
	@GetMapping(path="/notices")
	public ResponseEntity<Object>  retriveAllNotices() throws ClassNotFoundException, SQLException{
		List<Notices> list= noticeService.getAllNotices();
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
	}
	
	@GetMapping(path="/notices/{role}")
	public ResponseEntity<Object> retriveNoticeByRole(@PathVariable String role)throws ClassNotFoundException, SQLException {
		List<Notices> list= noticeService.getNoticeByRole(role);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.FETCHED,  list);
	}
	
	@GetMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> getNoticeByNoticeId(@PathVariable String noticeId) throws ClassNotFoundException, SQLException{
		Notices notice=noticeService.getNoticeByNoticeId(noticeId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_DELETED,  notice);
	}
	
	@PatchMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> updateNotice(@PathVariable String noticeId , @RequestBody Notices notice) throws ClassNotFoundException, SQLException{
		noticeService.updateNotice(noticeId,notice);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_UPDATED,  null);
	}
	
	@DeleteMapping(path="/notice/{noticeId}")
	public ResponseEntity<Object> deleteNotice(@PathVariable String noticeId) throws ClassNotFoundException, SQLException{
		noticeService.deleteNotice(noticeId);
		return ApiResponseHandler.buildResponse(ApiResponseStatus.SUCCESS, HttpStatus.OK, ApiMessages.NOTICE_DELETED,  null);
	}
}