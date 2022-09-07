package org.zerock.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

@Getter
@ToString(exclude = "pageList")
@Log
public class PageMaker<T> {
	private Page<T> result;
	
	private Pageable prevPage;	// 페이지 목록의 맨 앞인 '이전'으로 이동하는데 필요한 정보를 가진 Pageable
	private Pageable nextPage;	// 페이지 목록의 맨 뒤인 '다음'으로 이동하는데 필요한 정보를 가진 Pageable
	
	private int currentPageNum;	// 현재 페이지 수
	private int totalPageNum;	// 전체 페이지 수
	
	private Pageable currentPage;	// 현재 페이지의 정보를 가진 Pageable
	
	private List<Pageable> pageList;	// 페이지 번호의 시작부터 끝까지의 Pabeable들을 저장한 리스트
	
	public PageMaker(Page<T> result) {
		this.result = result;
		
		this.currentPage = result.getPageable();
		
		this.currentPageNum = result.getNumber() + 1;	// result.getNumber는 0부터 시작
		
		this.totalPageNum = result.getTotalPages();
		
		this.pageList = new ArrayList<Pageable>();
		
		calcPages();
	}
	
	private void calcPages() {
		// currentPageNum==1 => tempEndNum = 10, startNum = 1
		// currentPageNum==11 => tempEndNum = 20, startNum = 11
		// currentPageNum==21 => tempEndNum = 30, startNum = 21
		int tempEndNum = (int) (Math.ceil(this.currentPageNum/10.0)*10);
		int startNum = tempEndNum - 9;
		
		Pageable startPage = this.currentPage;
		
		// 현재 페이지 번호를 기준으로 (현재 페이지 번호-10)번째 Pageable 정보를 가져온다. (이전 버튼)
		for(int i=startNum; i<this.currentPageNum;i++) 
		{
			startPage = startPage.previousOrFirst();
		}
		this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();
		
		log.info("tempEndNum: " + tempEndNum);
		log.info("total: " + totalPageNum);
		
		// 전체페이지 개수가 10페이지단위 종료보다 작은 경우 끝 페이지 수를 변경 
		if(this.totalPageNum < tempEndNum) 
		{
			tempEndNum = this.totalPageNum;
			this.nextPage = null;	// 다음 버튼 비활성화
		}

		// 페이지 정보를 가지는 Pageable 객체를 리스트에 저장
		for(int i=startNum; i<=tempEndNum;i++) 
		{
			pageList.add(startPage);
			startPage = startPage.next();
		}
		// 다음 페이지가 남아있다면 다음 페이지 정보를 가지는 pageable 객체 저장
		this.nextPage = startPage.getPageNumber() +1 < totalPageNum ? startPage : null;
	}
}
