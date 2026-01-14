package com.kh.khEmail.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kh.khEmail.vo.AuthData;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	
	private final JavaMailSender sender;
	
	// { 이메일, 인증정보 } 
	private Map<String, AuthData> authInfo = new HashMap<>();
	
	/**
	 * 메일 전송 메소드
	 * @param subject 메일 제목
	 * @param text 메일 내용
	 * @param to 수신자
	 */
	public void sendMail(String subject, String text, String[] to) {
		SimpleMailMessage message = new SimpleMailMessage();
	
		// 메일 제목, 메일 내용, 수신자 정보를 SimpleMailMessage 객체에 담기
		message.setSubject(subject);
		message.setText(text);
		message.setTo(to);
		
		// JavaMailSender 를 통해 메일 전송
		sender.send(message);
	}
	
	/**
	 * HTML 형식의 메일 전송
	 * @param subject	제목
	 * @param text	내용
	 * @param to	수신자
	 * @throws MessagingException 
	 */
	public void sendHTMLMail(String subject, String text, String[] to) throws MessagingException {
		// MimeMessage 를 사용
		MimeMessage mm = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		
		helper.setSubject(subject);
		helper.setText(text, true);
		helper.setTo(to);
		
		sender.send(mm);
	}
	
	public void sendCode(String email) throws MessagingException {
		// 랜덤 코드 생성
		// String code = new AuthData().makeRandomCode("");	// ex) 1e2a5q
		
		// 랜덤 데이터 생성
		AuthData authData = new AuthData();
		String code = authData.getCode();
		
		
		String subject = "[KH] 인증코드";
		// String text = "인증코드 : " +  code;
		String text = "<p>아래의 인증코드를 입력해주세요.</p>"
						+ "<h3>" + code + "</h3>"
						+ "<p>인증 코드는 3분간 유효합니다.<p>";
		String[] to = { email };
		
		// 이메일(email)에 생성된 코드값을 Map에 저장
		// authInfo.put(email, code);
		// => 이메일에 생성된 인증 정보를 저장
		authInfo.put(email, authData);
		
		// sendMail(subject, text, to);
		sendHTMLMail(subject, text, to);
	}
	
	public void clearData() {
		long currTime = System.currentTimeMillis();
		
		// 유효시간이 지난 항목 제거
		authInfo.entrySet().removeIf(entry-> currTime > entry.getValue().getTime());
	}

	/**
	 * 해당 이메일에 발송된 인증코드와 전달받은 인증코드가 같은 값인지 확인
	 * @param email 이메일
	 * @param code 사용자가 전달한 인증코드
	 * @return
	 */
	public boolean checkCode(String email, String code) {
		// Map에서 email에 해당하는 코드를 찾아 --> get(key) : value
		// 전달된 code값과 동일한지 확인하여 결과를 반환
		
		// String authCode = authInfo.get(email);
		AuthData authData = authInfo.get(email);
		
		if (authData == null) return false;	// 이메일에 대한 발급코드가 없을 경우 => false
		
		String authCode = authData.getCode();
		boolean result = authCode.equals(code);	// 같으면 true, 다르면 false
		if (result) {
			// 인증코드 검증이 성공했을 때 Map에서 해당 이메일에 대한 정보 제거
			authInfo.remove(email);
		}
		
		return result;
	}	
}
