package com.society.Model;

public class Visitor {
	private String idVisitor;
	private String userId;
	private String name;
	private String purpose;
	private String arrivalTime;
	private String departureTime;
	private String arrivalDate;
	private String dep_date;
	private String approved;
	private String contactNo;
	public String getIdVisitor() {
		return idVisitor;
	}
	public void setIdVisitor(String idVisitor) {
		this.idVisitor = idVisitor;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public String getStatus() {
		return approved;
	}
	public void setStatus(String approved) {
		this.approved = approved;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	@Override
	public String toString() {
		return "Visitor [idVisitor=" + idVisitor + ", userId=" + userId + ", name=" + name + ", purpose=" + purpose
				+ ", arrivalTime=" + arrivalTime + ", departureTime=" + departureTime + ", arrivalDate=" + arrivalDate
				+ ", dep_date=" + dep_date + ", approved=" + approved + ", contactNo=" + contactNo + "]";
	}
	
}
