package com.kh.secureapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * * 입력 데이터 검증 및 표현 *
 * - 모든 외부 입력값(파라미터, JSON 등)은 '오염되었다'고 가정하고 반드시 검증해야 함.
 * - Validation 관련 어노테이션을 사용하면 유효하지 않은 데이터가 
 *   비즈니스 로직(DB 처리등)에 도달하기 전에 차단하여 SQL Injection, 시스템 장애 등을
 *   예방할 수 있음.
 * 
 * - Validation 관련 어노테이션
 *   + 필수값 체크 (누락 방지)
 *     - @NotBlank(message)  : null, "", " " 모두 허용하지 않음 (문자열 전용)
 *     - @NotNull(message)   : null 만 허용하지 않음 (모든 타입)
 *     
 *   + 크기 및 길이 체크
 *     - @Size(min, max, message) : 문자열 길이 또는 컬렉션 크기 제한
 *     
 *   + 숫자 범위 체크
 *     - @Min(value, message) : 최소값 제한
 *     - @Max(value, message) : 최대값 제한
 *     - @Positive(message)   : 양수만 허용
 *     - @PositiveOrZero(message) : 양수 또는 0만 허용
 *     
 *   + 형식 및 패턴 체크
 *     - @Email(message)  : 이메일 형식 유효성 검사
 *     - @Pattern(regexp, message) : 정규표현식을 이용한 유효성 검사
 *     
 *   + 날짜 체크
 *     - @Past(message)   : 과거 날짜만 허용
 *     - @Future(message) : 미래 날짜만 허용
 * 
 */

@Getter
@NoArgsConstructor
public class UserJoinRequest {
	
	// 필수항목. 길이는 4~12자 범위.
	@NotBlank(message="아이디는 필수 항목입니다.")
	@Size(min=4, max=12, message="아이디는 4~12자 사이여야 합니다.")
	private String userId;
	
	// 비밀번호.
	// - KISA(한국인터넷진흥원) 패스워드 가이드라인 : 10자리 이상, 두 종류 이상의 문자 구성
	// - 필수항목, 10자 이상/영문대소문자/숫자/특수문자(!@#$%^*()_-=+) 포함
	@NotBlank(message="비밀번호는 필수 항목입니다.")
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^*()_\\-+=])[A-Za-z\\d!@#$%^*()_\\-+=]{10,}$"
	, message="비밀번호는 10자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.")
	private String userPwd;
	
	// 이메일 형식
	@Email(message="이메일 형식이 올바르지 않습니다.")
	private String email;
	
	// 1살 이상만 허용
	@Min(value=1, message="나이는 1살 이상이어야 합니다.")
	private int age;

	@Override
	public String toString() {
		return userId + ", " + email + ", " + age;
	}
	
	// * 암호화된 비밀번호를 전달받아 userPwd 필드에 저장 메소드 (setter 역할)
	public void changeEncryptedPassword(String encPasswd) {
		this.userPwd = encPasswd;
	}
	
	
}
