package com.society.Model;

public class Services {
	private String idServices;
	private String userId;
	private String serviceName;
	private String description;
	private String status;
	public String getIdServices() {
		return idServices;
	}
	public void setIdServices(String idServices) {
		this.idServices = idServices;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Services [idServices=" + idServices + ", userId=" + userId + ", serviceName=" + serviceName
				+ ", description=" + description + ", status=" + status + "]";
	}
	
	

}
