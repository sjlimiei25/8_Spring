package com.kh.stockweb.stock.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kh.stockweb.stock.model.vo.Stock;

@Mapper
public interface StockMapper {
	
	@Select("SELECT * FROM STOCK WHERE NAME = #{name}")
	Stock findByName(@Param("name") String name);
	
	@Insert("INSERT INTO STOCK(NO, NAME, CODE, PRICE, MARKET_SUM) "
			+ " VALUES(SEQ_STNO.NEXTVAL, #{name}, #{code}, #{price}, #{marketCap})")
	int insertStock(Stock stock);

}
