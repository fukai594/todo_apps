package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserForm;


@Controller
public class UserController {
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if(error != null) {
			model.addAttribute("errorMessage","IDまたはパスワードが正しくありません。");
		}
        return "/login";
	}
	
	@GetMapping("/signup")
	public String getSignupPage() {
		return "/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Validated UserForm userForm, BindingResult bindingResult, Model model) {
		// バリデーションチェックでエラーがある場合は変更画面に戻る
		if (bindingResult.hasErrors()) {
			return "/signup";
		}
		return "/login";
	}
}
