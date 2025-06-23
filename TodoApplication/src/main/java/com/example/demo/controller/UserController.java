package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.form.UserForm;

@Controller
public class UserController {
	
	@GetMapping("/home")
	public String home(Model model) {
	 	UserForm userForm = new UserForm();
	 	model.addAttribute("userForm", userForm);
		return "/home";
	}
	@GetMapping("/login")
	public String login() {
        return "/login";
	}
}
