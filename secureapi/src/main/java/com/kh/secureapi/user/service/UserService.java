package com.kh.secureapi.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secureapi.user.dto.LoginRequest;
import com.kh.secureapi.user.dto.UserJoinRequest;
import com.kh.secureapi.user.mapper.UserMapper;
import com.kh.secureapi.user.vo.UserVO;

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
	
	// true/false => boolean 
	public boolean loginUser(LoginRequest request) {
		// LoginRequest --> 아이디(userId), 비밀번호(userPwd)
		
		// * 사용자가 입력한 아이디로 DB에서 조회
		String userId = request.getUserId();		// 사용자가 입력한 아이디 추출
		UserVO user = userMapper.findByUserId(userId);	// Mapper를 통해 DB에서 조회한 후 결과를 변수에 저장
		
		System.out.println(user);
		
		// * 사용자가 존재 여부 확인: 아이디에 해당하는 정보가 없으면 false 반환
		if(user == null) {
			
			return false;
		}
		
		// * 비밀번호 일치 여부 확인: 입력된 원본(평문) 비밀번호와 DB에 저장된 암호문를 매칭
		//		matches(원본비밀번호, 암호화된비밀번호) : 일치하면 true, 일치하지 않으면 false
		//   사용자가 입력한 비밀번호, DB에서 조회한 비밀번호
		//   => 원본비밀번호(평문) : 사용자가 입력한 비밀번호
		//   => 암호화된비밀번호   : DB에서 조회한 비밀번호
		boolean result = passwordEncoder.matches(request.getUserPwd(), user.getUserPwd());
		if (result) {
			return true;
		} else {
			// 예외 발생....
			return false;
		}
	}
}





