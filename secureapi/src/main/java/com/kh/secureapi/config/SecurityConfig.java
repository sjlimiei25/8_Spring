package com.kh.secureapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration		// 설정 관련 클래스 정의 시 사용
@EnableWebSecurity	// 스프링 시큐리티 활성화 시 사용 (생략 가능)
public class SecurityConfig {
	/*
	 * 암호화 처리 시 사용 클래스 : BCryptPasswordEncoder
	 * -> 스프링 시큐리티에서 제공하는 클래스
	 * -> 해당 클래스에 직접 @Component 등의 어노테이션을 추가할 수 없기 때문에,
	 * 	  @Bean 메소드를 통해 스프링 빈으로 등록하여 사용
	 */
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/*
	 * 스프링 시큐리티 기본 설정 비활성화
	 * - 기본 로그인 페이지 비활성화 : formLogin
	 * - CSRF 비활성화 : csrf
	 *   => 토큰 기반으로 인증 구조 설정 시 비활성화
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) {
		
		http.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable);
		
		return http.build();
		
	}
}





