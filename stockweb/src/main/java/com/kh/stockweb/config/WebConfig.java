package com.kh.stockweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		// CorsRegistry => 전역으로 CORS 설정
		registry.addMapping("/**")   // 어떤 경로(주소)에 CORS를 허용할 것인지. 전체 경로 설정
				.allowedOrigins("http://localhost:3000") // 허용할 출처(origin) 설정
				.allowedMethods("GET", "POST", "DELETE", "PUT") // 허용할 요청방식(method)설정
				.allowedHeaders("*") // 허용할 요청 헤더 설정
				.allowCredentials(true);	// 쿠키, 인증정보 포함 여부 설정
	
	}

	
	
}
