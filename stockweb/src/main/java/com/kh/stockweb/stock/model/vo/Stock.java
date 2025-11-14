package com.kh.stockweb.stock.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// VO(Value Object) : 데이터를 저장하기 위한 용도. 
//					  DB와 1:1로 매칭되는 객체
@Getter // 필드들의 getter 메소드를 추가(생성)
// @Setter // 필드들의 setter 메소드
// @NoArgsConstructor // 기본 생성자 정의
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자
@ToString // toString 메소드 재정의(모든필드의 값을 문자열로 리턴)
public class Stock {
	// {"name": "xxxx", "code":"xxxx", "price": "xxxx", "market_cap":"xxx"}
	private int no;
	private String name;
	private String code;
	private String price;
	private String marketCap;
	
//	@Override
//	public String toString() {
//		return String.format("[%s] %s", name, code);
//	}
}





