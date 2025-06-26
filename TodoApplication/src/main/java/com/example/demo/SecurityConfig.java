package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomAuthenticationFailureHandler;

@Configuration
public class SecurityConfig {
	@Autowired
	private CustomAuthenticationFailureHandler handler;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/css/**", "/signup").permitAll()
			.requestMatchers("/css/**", "/signup/**","/signup","/userRegisterComplete","/userRegisterComplete/**").permitAll()
		)
			.formLogin(login -> login// 指定したURLがリクエストされるとログイン認証を行う。
			.loginProcessingUrl("/login") 
			.loginPage("/login") // ログイン時のURLの指定
			.defaultSuccessUrl("/task/list", true)// 認証成功後にリダイレクトする場所の指定、強制的に遷移
			.failureHandler(handler) // カスタムハンドラーを設定
			.failureUrl("/login?error=true")
			.permitAll()
		).logout((logout) -> logout
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")
			.clearAuthentication(true)
		).authorizeHttpRequests(ahr -> ahr
			.requestMatchers("/logout").permitAll()
			.anyRequest().authenticated()//ログインページ以外のURLへはログイン後のみアクセス可能
		);
		return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {//パスワードのハッシュ化
		return new BCryptPasswordEncoder();
	}
}
