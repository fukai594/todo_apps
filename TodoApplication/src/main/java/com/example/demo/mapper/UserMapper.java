package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Account;

@Mapper
public interface UserMapper {
	Account findByLoginId(String loginId);
	int isExistUser(String loginId);
	void register(Account user);
	void updateLoginId(String loginId, String newLoginId);
	void updatePassword(String loginId, String password);
	void updateUserName(String loginId, String userName);
	Account getUser(String loginId);
	void incrementLoginFailureCount(String loginId);
	void resetLoginFailureCount(String loginId);
}
