package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Constants;
import com.example.demo.entity.Account;
import com.example.demo.form.UserForm;
import com.example.demo.repository.UserRepository;

@Component
@Transactional
public class AuthUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public AuthUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	};
	//何回失敗したらロックするか
	int lockingBoundaries = 5;
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
		Account account = userRepository
				.findByLoginId(loginId);
		if(account == null) {
			throw new UsernameNotFoundException("");
		}
		
		return User.withUsername(account.getLoginId())
				//パスワード
				.password(account.getPassword())
				//ログイン失敗5回以上でロック
				.accountLocked(account.getFailedLoginAttemps() >= lockingBoundaries)
				.build();
	}

	@Transactional
	public String register(UserForm userForm) {
		//変換処理
		Account account = convertToUser(userForm);
		//パスワードのエンコード
		String password = account.getPassword();
		account.setPassword(passwordEncoder.encode(password));
		//変更処理の場合の記述も実装予定
		userRepository.register(account);
		
		return Constants.USER_REGISTER_COMPLETE;
	}
	//ログイン失敗時のハンドラ
	@EventListener
	public void loginFailureHandler(AuthenticationFailureBadCredentialsEvent event) {
		String loginId = event.getAuthentication().getName();
		System.out.print("######################");
		System.out.print(event.getAuthentication().getName());
		userRepository.incrementLoginFailureCount(loginId);
	}
	//ログイン成功時のハンドラ
	@EventListener
	public void loginSuccessHandler(AuthenticationSuccessEvent event) {
		String loginId = event.getAuthentication().getName();
		userRepository.resetLoginFailureCount(loginId);
	}
	@Transactional
	public String updateLoginId(String loginId, String newLoginId) {
//		Account account = convertToUser(userForm);
//		String password = account.getPassword();
//		account.setPassword(passwordEncoder.encode(password));
		userRepository.updateLoginId(loginId, newLoginId);
		return Constants.USER_EDIT_COMPLETE;
	}
	
	@Transactional
	public String updatePassword(String loginId, String password) {
		userRepository.updatePassword(loginId, passwordEncoder.encode(password));
		return Constants.USER_EDIT_COMPLETE;
	}
	@Transactional
	public String updateUserName(String loginId, String userName) {
		userRepository.updateUserName(loginId, userName);
		return Constants.USER_EDIT_COMPLETE;
	}
	@Transactional
	public UserForm getUser(String loginId) {
		Account account = userRepository.getUser(loginId);
		//パスワードをデコードして返却
		return convertToUserForm(account);
	}
	
	
	public boolean isExistUser(String loginId) {//存在チェック
		int count = userRepository.isExistUser(loginId);
		if(count == 0) {
			return false;
		}
		return true;
	}
	public Account convertToUser(UserForm userForm) {
		Account account = new Account();
		account.setUserNo(userForm.getUserNo());
		account.setLoginId(userForm.getLoginId());
		account.setPassword(userForm.getPassword());
		account.setUserName(userForm.getUserName());
		account.setLastLogin(userForm.getLastLogin());
		account.setCreatedAt(userForm.getCreatedAt());
		return account;
	}
	public UserForm convertToUserForm(Account account) {
		UserForm userForm = new UserForm();
		userForm.setUserNo(account.getUserNo());
		userForm.setLoginId(account.getLoginId());
		userForm.setPassword(account.getPassword());
		userForm.setUserName(account.getUserName());
		userForm.setLastLogin(account.getLastLogin());
		userForm.setCreatedAt(account.getCreatedAt());
		return userForm;
	}
}
