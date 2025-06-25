package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {
	
	private int userNo;
// タイトルは1文字以上100文字以下
	@NotBlank
	@Size(min = 1, max = 100)
	private String loginId;
	
	private String password;
	
	private String userName;
	
	private int deleteFlg;
	
	private int failedLoginAttemps;
	
	private LocalDateTime lastLogin;
	
	private LocalDateTime createdAt;
	
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginid) {
		this.loginId = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	public int getFailedLoginAttemps() {
		return failedLoginAttemps;
	}
	public void setFailedLoginAttemps(int failedLoginAttemps) {
		this.failedLoginAttemps = failedLoginAttemps;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
