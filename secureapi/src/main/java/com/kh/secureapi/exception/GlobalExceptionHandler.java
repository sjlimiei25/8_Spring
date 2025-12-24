package com.kh.secureapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/*
 * * 예외 처리 *
 * - 예외 처리의 보안 목적
 *   + 시스템 내부 정보가 사용자에게 노출되는 것을 방지
 *   + 모든 예외 상황을 통제하여 시스템의 가용성 확보
 *   
 * - 스프링 예외 처리 단계 (범위)
 *   1) try~catch : 특정 로직에서 발생하는 세밀한 예외 처리
 *   2) @ExceptionHandler : 특정 컨트롤러 내의 공통 예외 처리
 *   3) @ControllerAdvice : 어플리케이션 전체 컨트롤러의 예외를 중앙 집중 관리
 *   
 * - 공통 처리 장점
 *   + 가독성 향상 : 비즈니스 로직과 예외 처리 로직 분리
 *   + 일관성 : 모든 에러 응답 형식을 통일하여 프론트엔트(클라이언트)와 협업 효율 증대
 *   + 유지보수 : 보안 정책(에러 메시지 변경 등) 변경 시 한 곳만 수정
 */

@RestControllerAdvice		// 모든 @RestController에서 발생하는 예외를 가로채서 처리
@Slf4j						// 로그 기록을 위한 어노테이션 (lombok)
public class GlobalExceptionHandler {
	
	/**
	 * 모든 예외에 대한 중앙 집중 처리
	 * 
	 * @param e 발생한 예외 객체. 발생 원인과 스택 트레이스 등의 정보를 담고 있음.
	 * @return  클라이언트에게 전달할 보안 처리된 에러 메시지와 HTTP 상태 코드를 담은 객체.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handlerAllException(Exception e) {
		
		// * 자세한 내용은 서버에 로그로 기록
		log.error("Unhandled Exception: ", e);
		
		// * 클라이언트에게는 공통 메시지를 전달
		Map<String, Object> response = new HashMap<>();
		// {"status": false,
		//  "message": "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요."}
		response.put("status", false);
		response.put("message", "시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
		
		// 응답코드가 200OK가 아닌 상황에 따라 전달 (400, 500 등)
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 존재하지 않는 경로에 대한 예외 처리 (404)
	 * 
	 * @param e NoHandlerFoundException
	 * @return 보안 처리된 JSON 응답 및 404 상태 코드
	 * 
	 * * 스프링에서는 기본적으로 404 발생 시 에러 페이지(HTML)를 찾으려 시도함.
	 * * 리액트와 같이 클라이언트와 연동하기 위한 API 서버인 경우,
	 * 	 일관된 JSON 응답을 위해 설정 추가 (=> application.properties)
	 * * 에러 메시지에 요청 경로(Path) 등을 포함하면 서버 구조 노출 위험이 있으므로 정제된 메시지를 전달. 
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<Map<String, Object>> handle404(NoHandlerFoundException e) {
		
		// 클라이언트에게 메시지 전달
		Map<String, Object> response = new HashMap<>();
		response.put("status", false);
		response.put("message", "잘못된 경로의 요청입니다.");
		// => 보안을 위해 경로를 노출하지 않음!
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);		
	}
	
	/**
	 * 입력값 검증 실패 시 처리
	 * -> 요청 데이터
	 * 
	 * @param e MethodArgumentNotValidException
	 * @return 검증 실패 메시지 및 400 상태 코드
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e) {
		
		Map<String, Object> response = new HashMap<>();
		response.put("status", false);
		
		// 에러 메시지를 validation에 지정한 메시지로 전달 (첫번째 항목만 전달)
		String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		response.put("message", msg);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}




