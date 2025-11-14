package com.kh.stockweb.stock.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kh.stockweb.stock.model.dto.StockDTO;
import com.kh.stockweb.stock.model.mapper.StockMapper;
import com.kh.stockweb.stock.model.vo.Stock;

@Service    // => 비즈니스 로직 처리 담당
public class StockService {
	
	@Value("${django.url}")
	private String djangoUrl;
	
	// ** 생성자 주입 방식으로 StockMapper 주입(lombok 안쓰는 가정하에 작성) **
	// 1) 주입할 객체 타입으로 필드 선언 (객체 생성 x)
	private final StockMapper mapper;
	// 2) 생성자를 통해 주입 -> 매개변수로 해당 타입이 존재해야 함
	public StockService(StockMapper mapper) {
		this.mapper = mapper;
	}
	
	// HTTP 요청용 객체 : RestTemplate (WebClinet)
	private final RestTemplate restTemplate = new RestTemplate();

	public Stock searchStock(String name) {
		// DB에 저장된 데이터가 있는 지 조회
		Stock stock = mapper.findByName(name);
		
		System.out.println(stock);
		
		// 조회된 데이터가 없을 경우, 장고 서비스로 요청
		if (stock == null) {
			// 장고(django) 서비스로 요청
			// -------------------------------------------------------------
			// - 요청 주소, 요청 방식(get), 요청 시 전달 데이터(name)
			// => http://localhost:8000/api/stock/?name=%EC%82%BC%EC%84%B1
			// String url = "http://localhost:8000/api/stock/?name=" + name;
			String url = djangoUrl + "/stock/?name=" + name;
			
			// Map<String, String> response = restTemplate.getForObject(url, Map.class);
			
			ResponseEntity<Map<String, String>> response = restTemplate.exchange(url
														, HttpMethod.GET
														, null
							, new ParameterizedTypeReference<Map<String, String>>(){});
			/*
			 * restTemplate.exchange(URL, httpMethod, requestEntity, responseEntity)
			 * - URL : 요청 주소
			 * - httpMethod : 요청 방식 (HttpMethod.GET, HttpMethod.POST, ...)
			 * - requestEntity : 요청 헤더와 본문을 포함
			 * 					 + HttpHeaders 로 헤더 정보 설정
			 * 					 + Map 구조로 본문 정보 전달
			 * - responseEntity : 응답 타입
			 * 					 + 제네릭 타입 안정성을 위해 ParameterizedTypeReference 이용
			 */
			
			// 응답 데이터만 추출
			Map<String, String> stockData = response.getBody();
			if (stockData != null) {
				String stName = stockData.get("name"); // => 종목명
				String stCode = stockData.get("code"); // => 코드
			 	String stPrice = stockData.get("price"); // => 현재가
				String stMarketSum = stockData.get("market_sum"); // => 시가총액
				
				// ------------------------------------------------------------------------
				
				// 스프링 서버에서 사용하는 객체에 담기
				StockDTO stockDto = new StockDTO(stName, stCode, stPrice, stMarketSum);
				
				// DB에 저장
				mapper.insertStock(stockDto);
				
				// DB에서 이름으로 조회하여 객체 반환 (name 컬럼이 유니크하다는 가정)
				stock = mapper.findByName(stName);
				
				return stock;
			}
		}
		
		return stock;
	}

}




