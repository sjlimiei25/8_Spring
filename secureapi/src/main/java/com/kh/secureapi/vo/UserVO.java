package com.kh.secureapi.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// VO : DB 테이블 구조를 표현하는 클래스
//		TB_USER 테이블
//		-> 컬럼명, 데이터타입을 기준으로 클래스를 정의
@NoArgsConstructor
@Setter
@Getter
public class UserVO {
	// 필드 4개 추가
	// USER_ID 컬럼 VARCHAR2(50) 타입
	private String userId;
	// USER_PWD 컬럼 VARCHAR2(100) 타입
	private String userPwd;
	// EMAIL VARCHAR2(100)
	private String email;
	// AGE NUMBER
	private int age;
}
