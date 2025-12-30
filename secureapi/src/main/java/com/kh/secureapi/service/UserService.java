package com.kh.secureapi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secureapi.dto.UserJoinRequest;
import com.kh.secureapi.mapper.UserMapper;
import com.kh.secureapi.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	// BCryptPasswordEncoder 객체를 주입 (생성자 주입방식, 롬복 사용)
	private final BCryptPasswordEncoder passwordEncoder;
	// UserMapper 주입
	private final UserMapper userMapper;
	
	public void registerUser(UserJoinRequest request) {
		
		// 비밀번호..........? => UserJoinRequest 클래스 내에서 확인
		
		// * 비밀번호 값이 사용자가 입력한 값 그대로 (평문, 원본)
		//   - 원본 비밀번호를 추출 => UserJoinRequest 객체에서 추출
		String orgPassword = request.getUserPwd();
		
		// * --- 암호화 --->
		//   - BCryptPasswordEncoder 의 encode() 메소드 이용
		//	   encode(원본비밀번호):암호화된비밀번호
		String encPassword = passwordEncoder.encode(orgPassword);
		
		// * 암호화된 비밀번호로 교체 (원래 값을 확인할 수 없는 상태)
		//   - UserJoinRequest 객체에 userPwd 필드에 저장 (setter)
		request.changeEncryptedPassword(encPassword);
		
		// 암호화된 비밀번호 출력
		System.out.println("암호화된 비밀번호 : " + encPassword);
		// => 단방향 해시로 복호화(원본으로 되돌리는 작업)가 불가능하여
		//	  데이터가 유출되어도 안전함
		//	  같은 비밀번호라도 암호화할 때마다 매번 다른 결과값을 보여줌
		
		// => DB 저장 ---- *
		//    1) 사용자 계정, 테이블 준비
		//    2) 필요한 라이브러리/프레임워크 준비 - MyBatis, ojdbc11
		UserVO user = new UserVO();
		user.setUserId(request.getUserId());
		user.setUserPwd(encPassword);
		user.setEmail(request.getEmail());
		user.setAge(request.getAge());
		
		userMapper.insert(user);
	}
}





