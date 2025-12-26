package com.kh.secureapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")		// CORS를 허용할 주소 설정 (요청받는 주소, /join)
				.allowedOrigins("http://localhost:3000")	// 허용할 출처(origin, 클라이언트 주소)
				.allowedMethods("POST");		// 허용할 요청방식 설정
		
	}

	
	
}
