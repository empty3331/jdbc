//
//Dao만들기
//
package com.javaex.author01;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {
		
		AuthorDao authorDao = new AuthorDao();
		
		
		//등록
		authorDao.authorInsert("이문열","경북 양양");
	    authorDao.authorInsert("-박경리","-경상남도 통영");
		
		//수정
		authorDao.authorUpdate(2,"박경리","경상남도 통영");
		
		//삭제
		authorDao.authorDelete(2);
		
		//리스트 가져오기
		List<AuthorVo>  authorList =  authorDao.getAuthorList();
		//authorDao.getAuthorList();
		
		//출력
		System.out.println("==========================");
		for(AuthorVo vo : authorList) {
			System.out.println(vo.getAuthorId()+","+vo.getAuthorName()+
					","+vo.getAuthorDesc());			
		}
		System.out.println("==========================");
	}

}
