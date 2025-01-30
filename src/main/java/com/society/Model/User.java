package com.society.Model;

import org.springframework.data.relational.core.mapping.Column;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

enum Role{
	GUARD, RESIDENT
}

public class User {
	
	
	private String idUser;
//	@Size(min=3)
	private String userName;
	//@NotNull(message="Invalid role")
	private String userRole;
//	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).{8,}$",
//	         message = "Password must contain at least one uppercase letter, one lowercase letter, one number, "
//	         		+ "one special character, and be at least 8 characters long.")
//	@Pattern(regexp = "^?=.*[A-Z]?=.*[a-z]?=.*[0-9]?=.*[(@#$%^&+=).].*$")
	private String password;
	@Digits(fraction = 0, integer = 10, message = "Phone number must be numeric and have up to 10 digits.")
	private String phoneNo;
	@Email
	private String email;
	private  String qrToken;
	@Column(value = "LONGTEXT")
	private  String qrImage;
	public String getQrToken() {
		return qrToken;
	}
	public void setQrToken(String qrToken) {
		this.qrToken = qrToken;
	}
	public String getQrImage() {
		return qrImage;
	}
	public void setQrImage(String qrImage) {
		this.qrImage = qrImage;
	}
	
	
	private String address;
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", userName=" + userName + ", userRole=" + userRole + ", password=" + password
				+ ", phoneNo=" + phoneNo + ", email=" + email + ", address=" + address + "]";
	}
	
	

}