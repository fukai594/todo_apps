package com.example.demo.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserPasswordForm {
	@Size(min = 8, max = 32, message="8から32文字で入力してください")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@!Q#$%&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,32}$", message = "パスワードの要件を満たしていません")
	private String password;

	private String confirmPassword;
	
	@AssertTrue(message="パスワードが一致しません。")
	public boolean isPasswordValid() {
		if(password == null || confirmPassword == null) {
			return false;
		}
		return password.equals(confirmPassword);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
