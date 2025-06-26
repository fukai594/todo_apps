package com.example.demo.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserForm {
	
	private int userNo;

	@NotBlank
	@Size(min = 1, max = 32)
	private String loginId;
	
	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@!Q#$%&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,32}$", message = "パスワードの要件を満たしていません")
	private String password;
	
	@NotBlank
	@Size(min = 1, max = 32)
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
	//不要
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
