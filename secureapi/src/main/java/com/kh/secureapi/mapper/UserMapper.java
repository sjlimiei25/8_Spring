package com.kh.secureapi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kh.secureapi.vo.UserVO;

@Mapper
public interface UserMapper {

	// 데이터(사용자정보) 추가 => Insert(DML)
	@Insert("INSERT INTO TB_USER (USER_ID, USER_PWD, EMAIL, AGE) "
			+ "VALUES (#{userId}, #{userPwd}, #{email}, #{age})")
	void insert(UserVO user);
}
