package com.society.serviceInterface;

import java.sql.SQLException;
import java.util.List;

import com.society.Model.Notices;

public interface NoticesServiceInterface {
	public boolean createNotice(Notices notice) throws SQLException, ClassNotFoundException ;
	public List<Notices> getNoticeByRole(String role) throws SQLException, ClassNotFoundException ;
	public List<Notices> getAllNotices() throws SQLException, ClassNotFoundException ;
	
	public boolean deleteNotice(String noticeId) throws SQLException, ClassNotFoundException ;
	public Notices getNoticeByNoticeId(String noticeId) throws ClassNotFoundException, SQLException;
	public boolean updateNotice(String noticeId, Notices notice) throws ClassNotFoundException, SQLException;
	
}
