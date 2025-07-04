package com.example.demo.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserNameForm {
	@Pattern(regexp="^[^\\s　]*$", message="スペースは使用できません")
	@Size(min = 1, max = 32, message="1から32文字で入力してください")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
