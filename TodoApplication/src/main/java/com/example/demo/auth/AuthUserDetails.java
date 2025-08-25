package com.example.demo.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Account;

public class AuthUserDetails implements UserDetails{

	//認証するユーザー情報
	private final Account account;
	private final Collection<? extends GrantedAuthority> authorities;
	
	//自作プロパティ

	
	public AuthUserDetails(Account account) {
		this.account = account;
		List<String> list = new ArrayList<String>(Arrays.asList(account.getUserName()));
		this.authorities = list
				.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.toList();
	}

	public Account getUser() {
		return account;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		//return null;
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		//return null;
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		//return null;
		return account.getUserName();
	}
	

	@Override
	public boolean isAccountNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		//return false;
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO 自動生成されたメソッド・スタブ
		//return false;
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO 自動生成されたメソッド・スタブ
		//return false;
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO 自動生成されたメソッド・スタブ
		//return false;
		return true;
	}
}
