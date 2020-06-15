package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsertTest {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");


		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");


		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO book VALUES (seq_book_id.nextval, ?, ? , ? , ? )";//쿼리문 문자열로 만들기, ? 주의
			pstmt = conn.prepareStatement(query);//쿼리로 만들기
			
			pstmt.setString(1,"26년");// ?(물음표) 중 1번째,순서중요
			pstmt.setString(2,"재미주의"); //?(물음표) 중 2번째
			pstmt.setString(3,"2012-02-04");
			pstmt.setInt(4,5);
			
			int count = pstmt.executeUpdate();
			
		    // 4.결과처리
			
			System.out.println(count+" 건 처리되었습니다.");
			

		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
		    // 5. 자원정리
		    try {
		        if (pstmt != null) {
		            pstmt.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } catch (SQLException e) {
		        System.out.println("error:" + e);
		    }


	}
 }

}
