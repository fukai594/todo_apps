package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@Repository
public class UserRepository {
	private final UserMapper userMapper;
	
	public UserRepository(UserMapper userMapper){
		this.userMapper = userMapper;
	}
	
	public User findByLoginId(String loginId) {
		return userMapper.findByLoginId(loginId);
	}
	
	public int isExistUser(String loginId) {
		return userMapper.isExistUser(loginId);
	}
	public void register(User user) {
		userMapper.register(user);
	}
}
