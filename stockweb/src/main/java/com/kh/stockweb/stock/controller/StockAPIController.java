package com.kh.stockweb.stock.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.stockweb.stock.model.vo.Stock;
import com.kh.stockweb.stock.service.StockService;
/*
 * JSON 형식/파일
 * {
 * 	"name": "홍길동",
 * 	"age": 20,
 * }
 * 
 */

//@CrossOrigin(origins="http://localhost:3000")
// => 스프링에서 제공하는 어노테이션. CORS 설정을 위해 사용.
//	  클래스, 메소드 단위에 설정
// -> origin : 프로토콜+도메인(ip)+포트
@RestController			// => @Controller + @ResponseBody
@RequestMapping("/api")	// => 현재 클래스에 매핑된 주소들에 대한 공통 주소 설정
public class StockAPIController {
	
	// * StockService 를 생성자 주입 방식으로 작성 *
	private final StockService service;
	public StockAPIController(StockService service) {
		this.service = service;
	}
	
	// ~/api/stock?name=xxxx
	@GetMapping("/stock")
	public Stock getStock(@RequestParam("name") String name) {
		Stock data = service.searchStock(name);
		return data;
	}
}







