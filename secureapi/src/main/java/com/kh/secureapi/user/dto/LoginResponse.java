package com.kh.secureapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
	// 사용자 아이디, 토큰
	private String userId;
	private String token; 
}
