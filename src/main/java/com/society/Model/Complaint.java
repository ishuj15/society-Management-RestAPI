package com.society.Model;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Complaint {
	@NotNull
	private String idComplaint;
	@NotNull
	private String userId;
	@Size(min=10)
	private String description;
	//@Present
	private Date date;
	@NotNull
	private String status;
	public String getIdComplaint() {
		return idComplaint;
	}
	public void setIdComplaint(String idComplaint) {
		this.idComplaint = idComplaint;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
