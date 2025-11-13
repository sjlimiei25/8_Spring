/*
    {
    "name": full_name,        # 종목명                 --> 문자타입
    "code": code,             # 종목코드                --> 문자타입
    "price": t_price,         # 현재가                     --> 문자타입
    "market_sum": market_sum  # 시가총액        --> 문자타입
  }        
*/
-- 테이블 -> 데이터베이스 객체
CREATE TABLE STOCK(
    NO NUMBER PRIMARY KEY, 
    NAME VARCHAR2(100),
    CODE VARCHAR2(30),
    PRICE VARCHAR2(30),
    MARKET_SUM VARCHAR2(100)
);

-- 시퀀스 -> 데이터베이스 객체
CREATE SEQUENCE SEQ_STNO
NOCYCLE
NOCACHE;





