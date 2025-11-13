package com.kh.stockweb.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.stockweb.stock.model.vo.Stock;
import com.kh.stockweb.stock.service.StockService;

import lombok.AllArgsConstructor;

@Controller		// => 요청을 받아 처리 후 응답하는 클래스에 지정 (빈 등록)
@AllArgsConstructor
public class StockController {
	// 생성자 주입방식 (DI)
	private final StockService service;

//  롬복(lombok) 사용 시 생략 가능 -> @AllArgsConstructor
//	public StockController(StockService service) {
//		this.service = service;
//	}
	
	// [POST] /search 주소로 요청이 들어왔을 때 요청을 받을 메소드 정의
	@PostMapping("/search")	   // Post 방식의 /search 주소의 요청이 왔을 때 처리할 메소드 지정
	// [접근제한자] [예약어] 반환형 메소드명(매개변수) {}
	public String searchStock(@RequestParam(value="name") String name) {
		// @RequestParam 정의한 변수 => 요청 시 전달되는 데이터 (키값: name)

		Stock stock = service.searchStock(name);
		/*
		 * {"name": "xxxx", "code":"xxxx", "price": "xxxx", "market_cap":"xxx"}
		 * -> 이 데이터를 저장할 모델 정의
		 */
		
		return null;
	}
}







