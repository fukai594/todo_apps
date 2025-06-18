package com.example.demo.form;

import jakarta.validation.constraints.NotNull;

public class CheckForm {
	@NotNull
	private boolean checkStatus;
	
	public boolean getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

}
