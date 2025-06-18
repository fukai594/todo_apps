package com.example.demo.entity;

import jakarta.validation.constraints.NotNull;

public class Check {
	@NotNull
	private boolean checkStatus;
	
	public boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}
}
