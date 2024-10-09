package com.society.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.society.Model.Notices;
import com.society.service.NoticeService;
@RestController
public class NoticesController {
	public NoticeService noticeService;
	public NoticesController(NoticeService noticeService) {		
		new NoticeService();
	}
	
	@PostMapping(path="/notice")
	public void createNotice(@RequestBody Notices notice) throws ClassNotFoundException, SQLException {
		noticeService.createNotice(notice);
	}
	@GetMapping(path="/notices")
	public List<Notices> retriveAllNotices() throws ClassNotFoundException, SQLException{
		return noticeService.retriveAllNotices();
	}
	@GetMapping(path="/notices/{role}")
	public List<Notices> retriveNoticeByRole(@PathVariable String role)throws ClassNotFoundException, SQLException {
		return noticeService.retriveNoticeByRole(role);
	}
	@PatchMapping(path="/notice/{noticeId}")
	public void updateNotice(@PathVariable String noticeId , @RequestBody Notices notice) throws ClassNotFoundException, SQLException{
		notice.setIdNotices(noticeId);
		noticeService.updateNotice(notice);
	}
	@DeleteMapping(path="/notice/{noticeId}")
	public void deleteNotice(@PathVariable String noticeId) throws ClassNotFoundException, SQLException{
		noticeService.deleteNotice(noticeId);
	}
}
