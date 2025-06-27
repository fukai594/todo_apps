package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.auth.AuthUserDetails;
import com.example.demo.common.Constants;
import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.repository.UserRepository;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public AuthUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	};
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
		User loginUser = userRepository.findByLoginId(loginId);
		AuthUserDetails user = new AuthUserDetails(loginUser);
		return user;
	}

	@Transactional
	public String register(UserForm userForm) {
		//変換処理
		User user = convertToUser(userForm);
		//パスワードのエンコード
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		
		//変更処理の場合の記述も実装予定
		userRepository.register(user);
		
		return Constants.USER_REGISTER_COMPLETE;
	}
	public boolean isExistUser(String loginId) {//存在チェック
		int count = userRepository.isExistUser(loginId);
		if(count == 0) {
			return false;
		}
		return true;
	}
	public User convertToUser(UserForm userForm) {
		User user = new User();
		user.setUserNo(userForm.getUserNo());
		user.setLoginId(userForm.getLoginId());
		user.setPassword(userForm.getPassword());
		user.setUserName(userForm.getUserName());
		user.setLastLogin(userForm.getLastLogin());
		user.setCreatedAt(userForm.getCreatedAt());
		
		return user;
	}
}
