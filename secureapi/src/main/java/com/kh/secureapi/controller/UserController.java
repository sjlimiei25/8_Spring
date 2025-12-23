package com.kh.secureapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secureapi.dto.UserJoinRequest;

import jakarta.validation.Valid;

@RestController
public class UserController {

	// [POST] /join
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserJoinRequest request) {
		
		// --- 비즈니스 로직 생략 --- => db 작업
		
		return ResponseEntity.ok("회원가입 성공");
		
	}
}




