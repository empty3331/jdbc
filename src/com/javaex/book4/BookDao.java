package com.javaex.book4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
	// 필드
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	// 생성자
	public BookDao() {
	}

	// g/s

	// 일반메소드
	private void getConnet() {
		try {// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
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

	// 책 추가
	public void bookInsert(BookVo bookVo) {
		getConnet();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO book VALUES (seq_book_id.nextval, ?, ? , ? , ? )";// 쿼리문 문자열로 만들기, ? 주의
			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setString(1, bookVo.getTitle());// ?(물음표) 중 1번째,순서중요
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());

			int count = pstmt.executeUpdate();

			// 4.결과처리

			System.out.println(count + " 권 추가되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	// 책 수정
	public void bookUpdate(BookVo bookVo) {
		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE book";
			query += " set title = ?,";
			query += "     pubs = ?,";
			query += "     pub_date = ?,";
			query += "     author_id = ?";
			query += " WHERE book_id = ?";

			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setString(1, bookVo.getTitle());
			pstmt.setString(2, bookVo.getPubs());
			pstmt.setString(3, bookVo.getPubDate());
			pstmt.setInt(4, bookVo.getAuthorId());
			pstmt.setInt(5, bookVo.getBookId());

			int count = pstmt.executeUpdate();// 쿼리문 실행

			// 4.결과처리

			System.out.println(count + " 권 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

	}

	// 책 삭제
	public void bookDelete(int bookId) {
		getConnet();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM book";
			query += " WHERE book_id = ?";
			pstmt = conn.prepareStatement(query);// 쿼리로 만들기

			pstmt.setInt(1, bookId);

			int count = pstmt.executeUpdate();// 쿼리문 실행

			// 4.결과처리
			System.out.println(count + " 권 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

	}

	// 책 리스트
	public List<BookVo> getBookList() {
		// 리스트 준비
		List<BookVo> bookList = new ArrayList<BookVo>();

		getConnet();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += "SELECT book_id,";
			query += "        title,";
			query += "        pubs,";
			query += "        pub_date,";
			query += "        author_id";
			query += " FROM book";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {
				int bookId = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");

				//
				BookVo bookVo = new BookVo(bookId, title, pubs, pubDate, authorId);
				bookList.add(bookVo);

				// System.out.println(bookId + "\t" + title + "\t" + pubs + "\t" + pubDate +
				// "\t" + authorId);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return bookList;
	}

}
