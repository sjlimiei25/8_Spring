package com.kh.secureapi.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secureapi.user.dto.LoginRequest;
import com.kh.secureapi.user.dto.UserJoinRequest;
import com.kh.secureapi.user.service.UserService;

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
	
	// [POST] /login
	//		로그인 요청 시 전달되는 데이터 : DTO (LoginRequest)
	//      응답 형태 : ResponseEntity
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
		// 전달된 데이터(아이디, 비밀번호)를 기준으로 유효한 사용자인지 확인 => 서비스
		boolean result = userService.loginUser(request);
		
		if (result) {
			// 유효한 사용자인 경우 "로그인 성공"/200ok 응답
			return ResponseEntity.ok("로그인 성공");
		} else {
			// 그렇지 않은 경우 "아이디 또는 비밀번호가 존재하지 않습니다."/401 응답
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)			// 응답 상태코드
								 .body("아이디 또는 비밀번호가 존재하지 않습니다.");	// 응답 데이터
		}
	}
	
}







