package com.Model;

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
	@Size(min=3)
	private String userName;
	//@NotNull(message="Invalid role")
	private String userRole;
	@Size(min=8)
	@Pattern(regexp = "^?=.*[A-Z]?=.*[a-z]?=.*[0-9]?=.*[(@#$%^&+=).].*$")
	private String password;
	@Digits(fraction = 0, integer = 10)
	private String phoneNo;
	@Email
	private String email;
	@NotNull
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
	
	

}
