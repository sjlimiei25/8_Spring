package com.kh.secureapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
	LoginRequest
	---------------------
	-userId:String		// 필수. 4~12자 사이
	-userPwd:String		// 필수. 10자 이상. 영문+숫자+특수문자
	---------------------
	+LoginRequest()		// 기본 생성자
	+getter
	+toString():String
	---------------------
 */
@NoArgsConstructor
@Getter
public class LoginRequest {

	@NotBlank(message="아이디는 필수 항목입니다.")
	@Size(min=4, max=12, message="아이디는 4~12자 사이여야 합니다.")
	private String userId;
	
	@NotBlank(message="비밀번호는 필수 항목입니다.")
	@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^*()_\\-+=])[A-Za-z\\d!@#$%^*()_\\-+=]{10,}$"
			, message="비밀번호는 10자 이상, 영문+숫자+특수문자를 포함해야 합니다.")
	private String userPwd;
	
	@Override
	public String toString() {
		// return userId + ", " + userPwd;
		return String.format("%s, %s", this.userId, this.userPwd);
	}	
	
}
