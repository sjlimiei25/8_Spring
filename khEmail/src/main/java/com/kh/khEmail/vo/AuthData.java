package com.kh.khEmail.vo;

import lombok.Data;

@Data
public class AuthData {
	private String code;		// 인증 코드
	private Long time;			// 인증 코드 생성 시간
	
	private final long LIMIT_TIME = 3 * 60 * 1000;	// 3분 제한
	
	public AuthData() {
		this.code = makeRandomCode("");
		this.time = System.currentTimeMillis() + LIMIT_TIME;
	}
	

	/**
	 * 랜덤코드값 생성
	 * @param code
	 * @return
	 */
	private String makeRandomCode(String code) {
		// 랜덤한 값(숫자, 문자)을 6자리 생성 
		
		// code의 길이가 6일 때 해당 값을 반환
		if (code.length() == 6) {
			return code;
		} else {
			// 6보다 작은 경우 랜덤값을 추출하여 code 변수에 값을 추가
			int random = (int)(Math.random() * 10);
			
			if (random % 2 == 0) {
				// - 짝수일 때 문자(소문자)
				int ran2 = (int)(Math.random() * ('z' - 'a' + 1) + 'a');
				code += (char)ran2;
			} else {
				// - 홀수일때 숫자 (0~9)
				code += random;
			}
			
			return makeRandomCode(code);
		}
	}		
}
