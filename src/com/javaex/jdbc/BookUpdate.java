package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookUpdate {

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
			String query = "";
			query += " UPDATE book";
			query += " set pubs = ?";
			query += " WHERE book_id = ?";
			
			pstmt = conn.prepareStatement(query);//쿼리로 만들기
			
			String pubs = "중앙Books";
			int no = 5; 
			
			pstmt.setString(1, pubs);
			pstmt.setInt(2, no);
			
			int count = pstmt.executeUpdate();//쿼리문 실행
		    
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
