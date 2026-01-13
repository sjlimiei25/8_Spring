package com.kh.secureapi.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.secureapi.security.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/*
 * JWT 인증 필터
 * : 요청 헤더에서 JWT토큰을 추출하고 검증 처리
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter 
			extends OncePerRequestFilter {
	// JwtUtil 객체를 주입 (생성자 주입방식)
	private final JwtUtil jwtUtil;
//	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//		this.jwtUtil = jwtUtil;
//	}

	@Override
    protected void doFilterInternal(HttpServletRequest request
    								, HttpServletResponse response
    								, FilterChain filterChain) 
			throws ServletException, IOException {
		// 요청 정보 중 헤더에서 Authorization 항목 추출
		// => 이 항목을 통해 토큰 정보를 받을 것!
		String authHeader = request.getHeader("Authorization");
		// 토큰이 있을 경우 => "Bearer 토큰값"
		// 토큰이 없을 경우 => null
		// 알수없는 토큰일 경우 => "XXXXX"
		
		// Authorization 헤더가 없거나, Bearer로 시작하지 않으면 다음 필터를 실행
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// Bearer 토큰이 있는 경우 검증 처리
		// "Bearer "를 제외한 토큰만 추출
		// => "Bearer X120sasd"
		String jwt = authHeader.substring(7);
		
		// 토큰 검증 및 사용자아이디(Subject) 추출
		String userId = jwtUtil.extractSubject(jwt);
		
		// 인증된 정보를 SecurityContext에 담기
		UsernamePasswordAuthenticationToken authToken =
				new UsernamePasswordAuthenticationToken(userId, null, List.of());
		
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		// 다음 필터 실행
		filterChain.doFilter(request, response);
    }
	
}
