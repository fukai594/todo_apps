package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.auth.AuthUserDetails;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepository;
	
	
	public AuthUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	};
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
		User loginUser = userRepository.findByLoginId(loginId);
		AuthUserDetails user = new AuthUserDetails(loginUser);
		User user1 = user.getUser();
		System.out.println(user1.getLoginId());
		System.out.println(user1.getLastLogin());
		return user;
	}
}
