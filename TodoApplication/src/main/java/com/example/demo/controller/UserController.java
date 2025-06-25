package com.example.demo.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.form.UserForm;
import com.example.demo.service.AuthUserDetailsServiceImpl;


@Controller
public class UserController {
	private final AuthUserDetailsServiceImpl userDetailsService;

    public UserController(AuthUserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
	
	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if(error != null) {
			model.addAttribute("errorMessage","IDまたはパスワードが正しくありません。");
		}
        return "/login";
	}
	
	@GetMapping("/signup")
	public String getSignupPage(Model model) {
		UserForm userForm = new UserForm();//userFormを返す
		model.addAttribute("userForm", userForm);
		return "/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// バリデーションチェックでエラーがある場合は変更画面に戻る
		if (bindingResult.hasErrors()) {
			return "/signup";
		}
		if(userDetailsService.isExistUser(userForm.getLoginId())) {
			model.addAttribute("signupError","loginId:"+ userForm.getLoginId() + "は既に存在します。");
			return "/signup";
		}
		try {
			String completeMessage = userDetailsService.register(userForm);
			redirectAttributes.addFlashAttribute("completeMessage", completeMessage);
			
		}catch(DataAccessException e) {
			model.addAttribute("signupError","ユーザー登録に失敗しました。");
			return "/signup";
		}
		
		return "redirect:/complete";
	}
}
