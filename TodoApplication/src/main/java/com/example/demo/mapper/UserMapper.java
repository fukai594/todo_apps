package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {
	User findByLoginId(String loginId);
	int isExistUser(String loginId);
	void register(User user);
	void updateLoginId(String loginId, String newLoginId);
	void updatePassword(String loginId, String password);
	void updateUserName(String loginId, String userName);
	User getUser(String loginId);
}
