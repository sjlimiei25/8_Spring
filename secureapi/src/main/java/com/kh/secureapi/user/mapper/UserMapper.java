package com.kh.secureapi.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.secureapi.user.vo.UserVO;

@Mapper
public interface UserMapper {

	// 데이터(사용자정보) 추가 => Insert(DML)
	@Insert("INSERT INTO TB_USER (USER_ID, USER_PWD, EMAIL, AGE) "
			+ "VALUES (#{userId}, #{userPwd}, #{email}, #{age})")
	void insert(UserVO user);
	
	// 데이터(아이디, 비밀번호) 조회 => Select(DQL)
	@Select("SELECT USER_ID, USER_PWD FROM TB_USER WHERE USER_ID = #{userId}")
	// 결과타입 : 
	//   VO => 기본키 또는 UNIQUE 컬럼이 조건으로 사용되었을 때. 결과가 1개의 행일 때!
	//   List => 결과가 여러 개의 행이 될때. Like...
	UserVO findByUserId(String userId);
	
}
