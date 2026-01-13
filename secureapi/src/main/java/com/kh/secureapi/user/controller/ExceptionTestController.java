package com.kh.secureapi.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test") // 공통 주소 설정 : /test
public class ExceptionTestController {
	
	// * 정상 동작 메소드 : [GET] /test/good
	@GetMapping("/good")
	public String goodMethod() {
		int result = 100 / 4;
		
		return "결과: " + result;
	}
	
	// * 비정상 동작 메소드 : [GET] /test/error
	//   10 / 0 의 결과 반환
	@GetMapping("/error")
	public String errorMethod() {
		// 고의로 에러 발생시킴!
		int result = 10 / 0;
		
		return "결과: " + result;
	}
}
