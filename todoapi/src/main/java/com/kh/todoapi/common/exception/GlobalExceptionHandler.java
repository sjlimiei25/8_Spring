package com.kh.todoapi.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.kh.todoapi.common.dto.response.ErrorResponse;
import com.kh.todoapi.common.enums.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 모든 에러 발생 시 처리
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handlerAllException(Exception e) {
		
		log.error("Unhandled Exception: ", e);
		
		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
							.body(ErrorResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode()
													, ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
								);
	}
	
	/**
	 * 잘못된 요청에 대한 처리
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handle404(NoHandlerFoundException e) {
		
		return ResponseEntity.status(ErrorCode.NOT_FOUND_PATH.getHttpStatus())
				.body(ErrorResponse.error(ErrorCode.NOT_FOUND_PATH.getCode()
										, ErrorCode.NOT_FOUND_PATH.getMessage())
					);		
	}
	
	/**
	 * 입력값 검증 실패 에러에 대한 처리
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
				
		// 에러 메시지를 validation에 지정한 메시지로 전달 (첫번째 항목만 전달)
		String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		String message = String.format("%s (%s)", ErrorCode.INVALID_DATA.getMessage(), msg);
		return ResponseEntity.status(ErrorCode.INVALID_DATA.getHttpStatus())
				.body(ErrorResponse.error(ErrorCode.INVALID_DATA.getCode()
										, message)
					);	
	}

}
