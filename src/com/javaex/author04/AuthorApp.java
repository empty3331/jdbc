//
//Dao만들,vo사용
//
package com.javaex.author04;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {
		
		AuthorDao authorDao = new AuthorDao();
		
		
		//등록
		AuthorVo vo01 = new AuthorVo("이문열","경북 양양");
		AuthorVo vo02 = new AuthorVo("-박경리","-경상남도 통영");
		
		authorDao.authorInsert(vo01);
	    authorDao.authorInsert(vo02);
		
		//수정
	    AuthorVo vo03 = new AuthorVo(2,"박경리","경상남도 통영");
		authorDao.authorUpdate(vo03);
		
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
