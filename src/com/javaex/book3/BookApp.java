package com.javaex.book3;

import java.util.List;

public class BookApp {

	public static void main(String[] args) {
		
		BookDao bookDao = new BookDao();
		
		//등록
		BookVo vo01 = new BookVo("우리들의 일그러진 영웅", "다림", "1998-02-22", 1);
		BookVo vo02 = new BookVo("이문열의 삼국지", "만음사", "2002-03-11", 1);
		bookDao.bookInsert(vo01);
		bookDao.bookInsert(vo02);
		
		//수정
		BookVo vo03 = new BookVo(2, "삼국지", "민음사", "2002-03-01", 1);
		bookDao.bookUpdate(vo03);
		
		//삭제
		bookDao.bookDelete(2);
		
		//리스트 가져오기
		List<BookVo> bookList  = bookDao.getBookList();
		
		//출력
		System.out.println("=======================================================");
		for(BookVo vo : bookList) {
			System.out.println(vo.getBookId()+". "+vo.getTitle()+","+vo.getPubs()+","+
		vo.getPubDate()+","+vo.getAuthorId());
		}
		System.out.println("=======================================================");
		
	}

}
