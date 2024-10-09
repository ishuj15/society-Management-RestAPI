package com.society.Model;

public class Notices {
	private String idNotices;
	private String title;
	private String message;
	private String targetRole;
	private String date;
	public String getIdNotices() {
		return idNotices;
	}
	public void setIdNotices(String idNotices) {
		this.idNotices = idNotices;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTargetRole() {
		return targetRole;
	}
	public void setTargetRole(String targetRole) {
		this.targetRole = targetRole;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Notices [idNotices=" + idNotices + ", title=" + title + ", message=" + message + ", targetRole="
				+ targetRole + ", date=" + date + "]";
	}
	

}
