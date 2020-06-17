package com.javaex.author04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
	
	//필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	//생성자
	public AuthorDao() {}
	
	//g/s
	
	//일반메소드
	
	//Connection
	private void getConnet() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}
	//자원정리
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
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
	
	//작가추가
	public void authorInsert(AuthorVo authorvo) {	
		getConnet();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO author VALUES (seq_author_id.nextval, ?, ? )";// 쿼리문 문자열로 만들기, ? 주의
			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setString(1, authorvo.getAuthorName());// ?(물음표) 중 1번째,순서중요
			pstmt.setString(2, authorvo.getAuthorDesc()); // ?(물음표) 중 2번째

			int count = pstmt.executeUpdate();

			// 4.결과처리

			System.out.println(count + " 건 처리되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

	}
	//작가수정
	public void authorUpdate(AuthorVo authorvo) {
		getConnet();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE author";
			query += " set author_name = ?,";
			query += "     author_desc = ?";
			query += " WHERE author_id = ?";

			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setString(1, authorvo.getAuthorName());
			pstmt.setString(2, authorvo.getAuthorDesc());
			pstmt.setInt(3, authorvo.getAuthorId());

			int count = pstmt.executeUpdate();// 쿼리문 실행

			// 4.결과처리

			System.out.println(count + " 건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

	}
	
	// 작가삭제
	public void authorDelete(int authorId) {
		getConnet();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM author";
			query += " WHERE author_id = ?";
			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setInt(1, authorId);

			int count = pstmt.executeUpdate();// 쿼리문 실행

			// 4.결과처리

			System.out.println(count + " 건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

	}

	//작가 리스트
	public List<AuthorVo> getAuthorList() {
		// 리스트 준비
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();

		getConnet();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT author_id,";
			query += "       author_name,";
			query += "       author_desc";
			query += " FROM author";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {
				int authorId = rs.getInt("author_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");

				//
				AuthorVo authorvo = new AuthorVo(authorId, authorName, authorDesc);
				authorList.add(authorvo);

				// System.out.println(authorId + "\t" + authorName + "\t" + authorDesc);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return authorList;

	}
}
