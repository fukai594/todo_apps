package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;


@Controller
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/home")
	public String home(Model model) {
	 	UserForm userForm = new UserForm();
	 	model.addAttribute("userForm", userForm);
		return "/home";
	}
	@RequestMapping("/success")
	public String success() {
		//TODO:セッション取得しタスクコントローラーのtask/listへ値を渡す
		return "task/list";
	}
}
