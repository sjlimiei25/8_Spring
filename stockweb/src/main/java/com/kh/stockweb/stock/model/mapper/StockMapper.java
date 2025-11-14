package com.kh.stockweb.stock.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.stockweb.stock.model.dto.StockDTO;
import com.kh.stockweb.stock.model.vo.Stock;

@Mapper
public interface StockMapper {
	
//	@Select("SELECT * FROM STOCK WHERE NAME = #{name}")  // 해당 데이터 찾기
	
	// 키워드 검색
//	@Select("SELECT * FROM STOCK WHERE NAME LIKE '%${name}%'")		// '한화' -> '%한화%'
	@Select("SELECT * FROM STOCK WHERE NAME LIKE '%' || #{name} || '%'")
	Stock findByName(@Param("name") String name);
	
	@Insert("INSERT INTO STOCK(NO, NAME, CODE, PRICE, MARKET_SUM) "
			+ " VALUES(SEQ_STNO.NEXTVAL, #{name}, #{code}, #{price}, #{marketCap})")
	int insertStock(StockDTO stock);

}
