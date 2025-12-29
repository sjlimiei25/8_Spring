package com.kh.secureapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secureapi.dto.UserJoinRequest;
import com.kh.secureapi.service.UserService;

import jakarta.validation.Valid;

@RestController
// @CrossOrigin("http://localhost:3000")
public class UserController {
	
	// private UserService service = new UserService();
	
	// UserSerivce 객체를 주입 (생성자 주입 방식)
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// [POST] /join
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserJoinRequest request) {
		
		// --- 비즈니스 로직 생략 --- => db 작업
		// * db 작업 --> 전달된 값들을 DB에 추가 (Create)
		userService.registerUser(request);
		
		return ResponseEntity.ok("회원가입 성공");
		
	}
}




