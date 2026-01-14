package com.kh.todoapi.common.enums;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 에러 관련된 메시지를 하나로 관리하기 위한 객체
 * enum (열거형 타입) 활용
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON01", "시스템 오류가 발생했습니다."),
	INVALID_DATA(HttpStatus.BAD_REQUEST, "COMMON02", "잘못된 입력값입니다."),
	MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON03", "필수 항목이 누락되었습니다."),
	NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "COMMON04", "잘못된 경로의 요청입니다.");
	
	
	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

}
