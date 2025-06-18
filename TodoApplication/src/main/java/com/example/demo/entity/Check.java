package com.example.demo.entity;

import jakarta.validation.constraints.NotNull;

public class Check {
	@NotNull
	private boolean checkStatus1=false;
	
	private boolean checkStatus2=false;
	
	private boolean checkStatus3=false;
	
	private boolean checkPriority1=false;
	
	private boolean checkPriority2=false;
	
	private boolean checkPriority3=false;
	
	private boolean checkPriority4=false;
	
	public boolean getCheckStatus1() {
		return checkStatus1;
	}

	public void setCheckStatus1(boolean checkStatus1) {
		this.checkStatus1 = checkStatus1;
	}

	public boolean getCheckStatus2() {
		return checkStatus2;
	}

	public void setCheckStatus2(boolean checkStatus2) {
		this.checkStatus2 = checkStatus2;
	}

	public boolean getCheckStatus3() {
		return checkStatus3;
	}

	public void setCheckStatus3(boolean checkStatus3) {
		this.checkStatus3 = checkStatus3;
	}

	public boolean getCheckPriority1() {
		return checkPriority1;
	}

	public void setCheckPriority1(boolean checkPriority1) {
		this.checkPriority1 = checkPriority1;
	}

	public boolean getCheckPriority2() {
		return checkPriority2;
	}

	public void setCheckPriority2(boolean checkPriority2) {
		this.checkPriority2 = checkPriority2;
	}

	public boolean getCheckPriority3() {
		return checkPriority3;
	}

	public void setCheckPriority3(boolean checkPriority3) {
		this.checkPriority3 = checkPriority3;
	}

	public boolean getCheckPriority4() {
		return checkPriority4;
	}

	public void setCheckPriority4(boolean checkPriority4) {
		this.checkPriority4 = checkPriority4;
	}
}
