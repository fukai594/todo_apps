package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	private final UserDetailsService userDetailsService;
	public  SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.formLogin(login -> login// 指定したURLがリクエストされるとログイン認証を行う。
			.loginProcessingUrl("/login") 
			.loginPage("/login") // ログイン時のURLの指定
			.defaultSuccessUrl("/task/list")// 認証成功後にリダイレクトする場所の指定
			.failureUrl("/login?error=true") 
			.permitAll()
		).logout(logout -> logout
			.logoutSuccessUrl("/login")
		).authorizeHttpRequests(ahr -> ahr
		.anyRequest().authenticated()//ログインページ以外のURLへはログイン後のみアクセス可能
		);
		return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {//パスワードのハッシュ化
		return new BCryptPasswordEncoder();
	}
}
