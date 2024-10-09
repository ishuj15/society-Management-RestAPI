package com.society.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.Model.Notices;
import com.society.dao.implementation.NoticesDAO;

@Service
public class NoticeService {
	public NoticesDAO noticeDao = new NoticesDAO();
	public void createNotice(Notices notice) throws ClassNotFoundException, SQLException {
	noticeDao.addNotice(notice);	
	}
	
	public List<Notices> retriveAllNotices()throws ClassNotFoundException, SQLException  {
		return noticeDao.getAllNotices();
	}
	public List<Notices> retriveNoticeByRole(String role)throws ClassNotFoundException, SQLException  {
		return noticeDao.getNoticeByRole(role);
	}
	
	public void updateNotice(Notices notice)throws ClassNotFoundException, SQLException  {
		//should use model or arraylist?
	}
	public void deleteNotice(String noticeId) throws ClassNotFoundException, SQLException {
		noticeDao.deleteNotice(noticeId);
	}

}
