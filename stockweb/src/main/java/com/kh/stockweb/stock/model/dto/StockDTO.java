package com.kh.stockweb.stock.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockDTO {
	private String name;
	private String code;
	private String price;
	private String marketCap;
}
