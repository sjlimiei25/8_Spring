package com.kh.secureapi.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)
 * - 공통 관심사(로깅, 트랜잭션, 보안 등)를
 *   핵심 비즈니스 로직과 분리하여 관리하는 프로그래밍 기법
 *   => 핵심 로직과 분리함으로써 가독성와 유지보수성을 높일 수 있음
 *   
 *   + 핵심 로직 : controller / service / repository(dao)
 *   + 공통 로직 : logging / exception / security
 */
@Aspect		// 이 클래스가 AOP 객체임을 선언
			// 메소드 실행 전/후 또는 예외 발생 시점에 로깅과 같은 공통 기능을 횡단적으로 적용할 수 있음
@Component	// 스프링에서 빈으로 등록되어 관리 대상이 되도록 함.
@Slf4j
public class LoggingAOP {
	/*
	 * * 실행 시점 (Advice) *
	 * 
	 * 		@Around		: 메소드 실행 전/후 제어. 실행 시간 측정 시 사용
	 * 		@Before		: 메소드 실행 전 공통 로직 수행.
	 * 		@After		: 메소드 실행 후 공통 로직 수행. 항상 실행. (정상 동작/예외 발생 상관없이!)
	 * 		@AfterReturning	: 메소드가 "정상적으로" 리턴되었을 때만 실행. (결과값 사용 가능)
	 * 		@AfterThrowing  : 메소드에서 "예외가 발생" 했을 때만 실행. (예외 객체 사용 가능)
	 * 
	 *  --> 실행 흐름
	 *  	: @Around(전) -> @Before -> [대상 메소드] -> @AfterReturning -> @After -> @Around(후)
	 */
	
	/*
	 * * 적용 대상 (pointcut) *
	 * 
	 * 	@Pointcut		: 어디에 적용할 지 정의하는 식
	 * 	 - execution	: 리턴 타입 + 패키지 + 클래스 + 메소드 + 파라미터 조합. 가장 많이 사용되는 식.
	 * 	 - within		: 특정 패키지 또는 클래스 내부의 모든 메소드.
	 *   - target		: 특정 인터페이스를 구현한 객체의 모든 메소드.
	 */
	
	// 대상 지정 : com.kh.secureapi 패키지 하위 패키지 중 controller 패키지 내에 있는 모든 클래스의 모든 메소드
	@Pointcut("execution (* com.kh.secureapi..controller.*.*(..) )")
	private void cut() {}
	//  * : 모든 리턴 타입/모든 클래스/모든 메소드
	//  .. : 하위 패키지 전체/매개변수 개수 상관없이
	
	// 대상 메소드가 실행되기 전에 공통 로직을 동작 => @Before
	@Before("cut()")
	public void before(JoinPoint joinPoint) {
		// * JoinPoint : 프로그램 실행 중 특정 지점을 나타내는 객체. 일반적으로 메소드 실행을 의미함.
		
		// 메소드명과 메소드에 전달된 매개변수 정보를 출력
		// - 메소드명 추출
		String methodName = joinPoint.getSignature().getName();
		
		// - 매개변수 정보 추출
		Object[] args = joinPoint.getArgs();
		
		log.info("=================== before method ==================");
		log.info("* method name : {}", methodName);
		log.info("* parameter : {}", Arrays.toString(args));
	}
	
	// 대상 메소드가 정상 동작된 후 실행 => @AfterReturning
	@AfterReturning(value="cut()", returning="obj")
	public void afterReturn(JoinPoint joinPoint, Object obj) {
		// * 메소드명 추출
		String methodName = joinPoint.getSignature().getName();
		
		log.info("============= after return method =============");
		log.info("* method name : {}", methodName);
		log.info("* result : {}", obj);
	}
	
	// 대상 메소드에서 예외 발생 시 실행 => @AfterThrowing
	@AfterThrowing(value="cut()", throwing="ex")
	public void afterThrowing(JoinPoint joinPoint, Exception ex) {
		log.error("============ after throwing ==================");
		log.error("* method name : {}", joinPoint.getSignature().getName());
		log.error("* error message : {}", ex.getMessage());
	}
	
	
	// 대상 메소드 실행 전/후 동작 => @Around
	// - 시간 측정 => 메소드 호출 전 시작 시간 측정, 메소드 호출 후 종료 시간 측정
	@Around("cut()")
	public Object around(ProceedingJoinPoint prcJoinPoint) throws Throwable {
		
		// 대상 메소드 실행 전
		//    시작 시간 측정
		long startTime = System.currentTimeMillis();
		
		// 대상 메소드 실행
		Object result = prcJoinPoint.proceed();
		
		// 대상 메소드 실행 후
		//	  종료 시간 측정
		long endTime = System.currentTimeMillis();
		
		long proceedTime = endTime - startTime;
		
		log.info("================ around method ==============");
		log.info("* method name : {}", prcJoinPoint.getSignature().getName());
		log.info("* processing time : {}", proceedTime);
		
		return result;
	}
}







