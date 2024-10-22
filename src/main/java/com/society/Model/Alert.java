package com.society.Model;

import java.sql.Date;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Alert {
	private String idAlert;
	@NotNull
	@Size(min=10)
	private String message;
	@FutureOrPresent()
	private Date date;
	private String targetRole;
	public String getIdAlert() {
		return idAlert;
	}
	public void setIdAlert(String idAlert) {
		this.idAlert = idAlert;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTargetRole() {
		return targetRole;
	}
	public void setTargetRole(String targetRole) {
		this.targetRole = targetRole;
	}
	
}
	

