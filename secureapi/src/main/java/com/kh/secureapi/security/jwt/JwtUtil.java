package com.kh.secureapi.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import com.kh.secureapi.user.vo.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Integer expiration;
	
	private static final String TOKEN_TYPE_ACCESS = "access";
	
	/**
	 * 서명용 암호화 키 객체 생성
	 * @return JWT 서명 및 검증용 SecretKey 객체
	 */
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	/**
	 * 토큰 생성하는 메소드
	 * @param claims		사용자 정보 (이메일, 권한 등..)
	 * @param subject		고유 식별자 (사용자 아이디, 이메일, ..)
	 * @return	JWT(토큰)
	 */
	public String createToken(Map<String, Object> claims
							, String subject) {
		return Jwts.builder()
				   .claims(claims)
				   .subject(subject)
				   .issuedAt(new Date(System.currentTimeMillis()))	// 토큰 발행 시간
				   .expiration(new Date(System.currentTimeMillis() + expiration)) // 토큰 만료시간
				   .signWith(getSigningKey()) // 서명 정보(수행)
				   .compact(); // 토큰 생성
	}
	
	/**
	 * Access 토큰 생성
	 * @param user 사용자 정보
	 * @return JWT(Access 토큰)
	 */
	public String generateAccessToken(UserVO user) {
		
		Map<String, Object> claims = new HashMap<>(); 
		claims.put("type", TOKEN_TYPE_ACCESS);
		claims.put("email", user.getEmail());
		
		return createToken(claims, user.getUserId());
	}
	
	/**
	 * 토큰 검증 및 정보 추출
	 * @param token 검증 및 추출 대상 토큰
	 * @return 사용자 정보(Claims)
	 */
	public Claims extractAllClaims(String token) {
		return Jwts.parser()
					.verifyWith(getSigningKey())
					.build()		// 토큰 검증 준비 작업..
					.parseSignedClaims(token)
					.getPayload();
	}
	
	/**
	 * 토큰 검증 및 식별정보(subject) 추출
	 * @param token 검증할 토큰
	 * @return 식별 정보(subject)
	 */
	public String extractSubject(String token) {
		return extractAllClaims(token).getSubject();
	}
	
	// 토큰 유효성 확인
	/**
	 * 토큰 유효성 확인 
	 * @param token   대상 토큰
	 * @param subject 식별 정보
	 * @return 유효한 토큰이면 true, 유효하지 않으면 false
	 */
	public boolean isTokenValid(String token, String subject) {
		// 토큰에서 subject 추출
		String extractSub = extractSubject(token);
		
		// 추출된 subject와 전달된 subject 비교
		boolean isValidSub = extractSub.equals(subject);
		
		// 유효시간 추출 (payload)
		Date expr = extractAllClaims(token).getExpiration();
		
		// 추출된 유효시간과 현재시간 비교 -> before
		boolean isValidTime = expr.before(new Date());
		
		return isValidSub && isValidTime;
	}
}







