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
	
	@Transactional
	public String updateLoginId(String loginId, String newLoginId) {
//		User user = convertToUser(userForm);
//		String password = user.getPassword();
//		user.setPassword(passwordEncoder.encode(password));
		userRepository.updateLoginId(loginId, newLoginId);
		return Constants.USER_EDIT_COMPLETE;
	}
	@Transactional
	public UserForm getUser(String loginId) {
		User user = userRepository.getUser(loginId);
		//パスワードをデコードして返却
		return convertToUserForm(user);
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
	public UserForm convertToUserForm(User user) {
		UserForm userForm = new UserForm();
		userForm.setUserNo(user.getUserNo());
		userForm.setLoginId(user.getLoginId());
		userForm.setPassword(user.getPassword());
		userForm.setUserName(user.getUserName());
		userForm.setLastLogin(user.getLastLogin());
		userForm.setCreatedAt(user.getCreatedAt());
		return userForm;
	}
}
