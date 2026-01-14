package com.kh.khEmail.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.khEmail.dto.MailRequest;
import com.kh.khEmail.service.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {
	private final MailService mService;
	
	/**
	 * 전달된 이메일로 인증코드 발생
	 * @param request email: 수신자 이메일
	 * @return
	 * @throws Exception
	 */
	@PostMapping("mail")
	public String sendAuth(@RequestBody MailRequest request) throws Exception {
		String email = request.getEmail();
		
		if (email == null) {
			throw new Exception("필수 항목이 없습니다. (email)");
		}
		
		log.info("* email : {}", email);
		
		try {
			mService.sendCode(email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "ok";
	}
	
	/**
	 * 인증코드 검증
	 * @param request email: 수신자 이메일, code: 인증코드
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/check")
	public String checkCode(@RequestBody MailRequest request) throws Exception {
		String email = request.getEmail();
		String code = request.getCode();
		
		if (email == null || code == null) {
			throw new Exception("필수 데이터가 전달되지 않았습니다. (email, code)");
		}
		
		log.info("* email: {}, code: {}", email, code);
		
		boolean result = mService.checkCode(email, code);
		if (result) {
			return "success";
		} else {
			return "failed";
		}
	}
}
