package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("/success") //configのdefaultSuccessUrlでhelloに来るように指定したので
	public String hello(Authentication loginUser,Model model) {

		/*AuthenticationでログインUserの情報を使うことができるので
		modelを使って、"username"にusernameを詰める*/

		model.addAttribute("username",loginUser.getName());
		
		return "success";//helloを表示。usernameをhelloで表示できる
	}
	@GetMapping("/login")
	public String login() {

        return "/login";
	}
}
