package com.kh.todoapi.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	private String message;
	private String errorCode;
	
	public static ErrorResponse error(String message, String errorCode) {
		return new ErrorResponse(message, errorCode);
	}

}
