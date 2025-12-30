-- 관리자 계정 : sys as sysdba / oracle

-- * 계정 생성 및 권한 부여 *
--   사용자명 / 비밀번호
--   C##SECUREAPI / SECUREAPI

-- 사용자 계정 생성 => "DDL" / DML / DQL / DCL
--     DDL (데이터 정의 언어)   : CREATE, ALTER, DROP
--     DML (데이터 조작 언어)  : INSERT, UPDATE, DELETE
--      DQL (데이터 질의 언어) : SELECT
--      DCL (데이터 제어 언어) : GRANT, REVOKE
CREATE USER C##SECUREAPI IDENTIFIED BY SECUREAPI;

-- 권한 부여 => DCL
GRANT CONNECT, RESOURCE TO C##SECUREAPI;

-- 테이블 스페이스 설정 => DDL
ALTER USER C##SECUREAPI DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
--============================================================

-- C##SECUREAPI / SECUREAPI 계정
/*
    테이블 생성
    
    CREATE TABLE 테이블명 (
        컬럼명 데이터타입 제약조건,
        컬럼명 데이터타입 제약조건,
        ...
    );
*/
CREATE TABLE TB_USER (
    USER_ID VARCHAR2(50) PRIMARY KEY,       -- PRIMARY KEY : NOT NULL + UNIQUE
    USER_PWD VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) NOT NULL,
    AGE NUMBER 
);

SELECT * FROM TB_USER;

-- * 데이터 추가
--    INSERT INTO 테이블명 (컬럼명, 컬럼명, ...) VALUES (데이터, 데이터,  ...)
--    INSERT INTO 테이블명 VALUES (데이터 ,... ) 
-- 아이디: admin, 비밀번호: 1234, 이메일: admin@kh.co.kr, 나이: 20
INSERT INTO TB_USER (USER_ID, USER_PWD, EMAIL, AGE) VALUES ('admin', '1234', 'admin@kh.co.kr', 20);
-- 자바 : "값", 파이썬/자바스크립트 : '값', "값", 오라클: '값'

-- 트랜잭션 : DB에 변경사항(데이터 추가/수정/삭제, DML) 반영
--   * 적용 : COMMIT;
--   * 취소 (반영X) : ROLLBACK;
ROLLBACK;

