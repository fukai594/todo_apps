package com.example.demo.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserLoginIdForm {
	@Pattern(regexp="^[^\\s　]*$", message="スペースは使用できません")
	@Size(min = 1, max = 32, message="1から32文字で入力してください")
	private String loginId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
