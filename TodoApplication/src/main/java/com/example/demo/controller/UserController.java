package com.example.demo.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.AuthUserDetails;
import com.example.demo.form.UserForm;
import com.example.demo.form.UserLoginIdForm;
import com.example.demo.service.AuthUserDetailsServiceImpl;
import com.example.demo.service.TaskService;


@Controller
public class UserController {
	private final AuthUserDetailsServiceImpl userDetailsService;
	private final TaskService taskService;
	
    public UserController(AuthUserDetailsServiceImpl userDetailsService, TaskService taskService) {
        this.userDetailsService = userDetailsService;
        this.taskService = taskService;
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
	public String signup(
			@Validated UserForm userForm,
			BindingResult bindingResult,
			Model model
			) {
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
			model.addAttribute("completeMessage", completeMessage);
			
		}catch(DataAccessException e) {
			model.addAttribute("signupError","ユーザー登録に失敗しました。");
			return "/signup";
		}
		
		return "/userComplete";
	}
	@GetMapping("/userInfo")
	public String edit(
		Authentication loginUser,
		Model model
		) {
		//ログイン中のユーザーを取得
		String loginId = getLoginId(loginUser);
		UserForm userForm = userDetailsService.getUser(loginId);
		model.addAttribute("userForm",userForm);
		return "/userInfo";
	}
	
	@GetMapping("/editLoginId")
	public String editLoginId(
		UserLoginIdForm userLoginIdForm,
		Model model
		) {
		//ログイン中のユーザーを取得
		model.addAttribute("userLoginIdForm",userLoginIdForm);
		return "/editLoginId";
	}
	@PostMapping("/loginIdConfirm")
	public String confirmLoginId(
		@Validated UserLoginIdForm userLoginIdForm,
		BindingResult bindingResult,
		Model model){
		// バリデーションチェックでエラーがある場合は変更画面に戻る
		if (bindingResult.hasErrors()) {
			return "/editLoginId";
		}
		System.out.println("受信データ");
		System.out.println("loginId"+ userLoginIdForm.getLoginId());
		
		model.addAttribute("userLoginForm",userLoginIdForm);
		return "/loginIdConfirm";
	}
	
	@PostMapping("/save")
	public String saveUser(
			@Validated UserLoginIdForm userLoginIdForm,
			BindingResult bindingResult,
			Model model,
			Authentication loginUser
			) {
		if(bindingResult.hasErrors()) {
			return "/editLoginId";
		}
		if(userDetailsService.isExistUser(userLoginIdForm.getLoginId())) {
			model.addAttribute("userRegisterError","loginId:" + userLoginIdForm.getLoginId() + "は既に存在します。");
			return "/editLoginId";
		}
		try {
			//ログイン中のユーザーを取得
			System.out.println(userLoginIdForm.getLoginId());
			System.out.println(getLoginId(loginUser));
			//usersテーブルの更新
			String completeMessage = userDetailsService.updateLoginId(getLoginId(loginUser), userLoginIdForm.getLoginId());
			//taskテーブルの更新
			taskService.updateLoginId(getLoginId(loginUser), userLoginIdForm.getLoginId());
			model.addAttribute("completeMessage", completeMessage);
		}catch(DataAccessException e) {
			model.addAttribute("userRegisterError","ユーザー情報の更新に失敗しました。");
		}
		
		return "/userComplete";
	}
	//ログイン中のユーザー情報を取得
	private String getLoginId(Authentication loginUser) {
		AuthUserDetails userDetails = (AuthUserDetails)loginUser.getPrincipal();
		 return userDetails.getUser().getLoginId();
	}
	@GetMapping("/back")
	public String backToEditPage(
		UserForm userForm,
		Model model
		) {
		model.addAttribute("userForm", userForm);
		return "/edit";
	}
}
