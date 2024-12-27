package com.society.serviceImp;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.Model.Notices;
import com.society.constants.ApiMessages;
import com.society.dao.implementation.NoticesDAO;
import com.society.exceptions.NoticeException;
import com.society.serviceInterface.NoticesServiceInterface;

@Service
public class NoticeService implements NoticesServiceInterface {
	@Autowired
	public NoticesDAO noticeDao ;
	@Override
	public boolean createNotice(Notices notice) throws ClassNotFoundException, SQLException {
	if(!noticeDao.addNotice(notice))
		throw new NoticeException(ApiMessages.UNABLE_TO_CREATE_NOTICE);
	return true;

	}
	@Override
	public List<Notices> getAllNotices()throws ClassNotFoundException, SQLException  {
		List<Notices> notices = noticeDao.getAllNotices();
		if(notices.isEmpty())
			throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
		else
			return notices;
			
	}
	@Override
	public List<Notices> getNoticeByRole(String role)throws ClassNotFoundException, SQLException  {
		List<Notices> notices = noticeDao.getNoticeByRole(role);
		if(notices.isEmpty())
			throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
		else
			return notices;
	}
	
	@Override
	public Notices getNoticeByNoticeId(String noticeId) throws ClassNotFoundException, SQLException {
		Notices notice=noticeDao.getNoticeByNoticeId(noticeId); 
		if(notice==null)
			throw new NoticeException(ApiMessages.NOTICE_NOT_FOUND);
		
		return notice;

	}
	@Override
	public boolean updateNotice(String noticeId,Notices notice)throws ClassNotFoundException, SQLException  {
		Notices existingnotice= noticeDao.getNoticeByNoticeId(noticeId);
		
		if(existingnotice==null)
			throw new NoticeException(ApiMessages.UNABLE_TO_UPDATE_NOTICE);
		
		else
		{
			if(notice.getDate()!=null)
			{
				noticeDao.updateNotice(noticeId, "date", notice.getDate());
			}
			if(notice.getMessage()!=null)
			{
				noticeDao.updateNotice(noticeId, "message", notice.getMessage());
			}
			if(notice.getTargetRole()!=null)
			{
				noticeDao.updateNotice(noticeId, "targetRole", notice.getTargetRole());
			}
			if(notice.getTitle()!=null)
			{
				noticeDao.updateNotice(noticeId, "title", notice.getTitle());
			}
			return true;

		}
		
	}
	@Override
	public boolean deleteNotice(String noticeId) throws ClassNotFoundException, SQLException {
		if(!noticeDao.deleteNotice(noticeId))
			throw new NoticeException(ApiMessages.UNABLE_TO_DELETE_NOTICE);
		return true;

	}

}
