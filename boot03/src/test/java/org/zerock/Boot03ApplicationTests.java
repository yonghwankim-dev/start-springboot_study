package org.zerock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.domain.Board;
import org.zerock.domain.QBoard;
import org.zerock.persistence.BoardRepository;

import com.querydsl.core.BooleanBuilder;

@SpringBootTest
class Boot03ApplicationTests {

	@Autowired
	private BoardRepository repo;
	
	// 특정 테스트 유닛의 결과를 확인하고 싶을때 @Disabled 어노테이션 제거
	
	@Disabled	// 해당 애노테이션 설정시 테스트 비활성화 수행
	@Test
	void testInsert200() {
		// 게시물 샘플 200개 생성
		for(int i=1;i<=200;i++)
		{
			Board board = new Board();
			board.setTitle("제목.. " + i);
			board.setContent("내용..." + i + " 채우기");
			board.setWriter("user0" + (i%10));
			repo.save(board);
		}
	}
	
	@Disabled
	@Test
	void testByTitle() {
		// 게시물에서 제목이 '제목.. 177'인 게시물 검색
		repo.findBoardByTitle("제목.. 177").forEach(board->System.out.println(board));		
	}
	
	@Disabled
	@Test
	void testByWriter() {
		// 게시물에서 작성자 이름이 'user00'인 게시물 검색
		Collection<Board> results = repo.findByWriter("user00");
		
		results.forEach(board->System.out.println(board));
	}
	
	@Disabled
	@Test
	void testByWriterContaining() {
		// 게시물에서 작성자 이름에 '05'라는 문자가 들어있는 게시물 검색
		Collection<Board> results = repo.findByWriterContaining("05");
		
		results.forEach(board->System.out.println(board));	
	}
	
	@Disabled
	@Test
	void testByTitleContainingOrContentContaining() {
		// 게시물에서 제목이 '제목.. 25' 이거나 게시물 내용이 '내용...35'인 게시물 검색
		Collection<Board> results = repo.findByTitleContainingOrContentContaining("제목.. 25", "내용...35");
		
		results.forEach(board->System.out.println(board));
	}
	
	@Disabled
	@Test
	void testByTitleAndBno() {
		// 게시물에서 제목에 '5'가 포함되어 있고 게시물의 번호가 50보다 큰 게시물 검색
		Collection<Board> results = repo.findByTitleContainingAndBnoGreaterThan("5", 50);
		
		results.forEach(board->System.out.println(board));
	}
	
	@Disabled
	@Test
	void testBnoOrderBy() {
		// 게시물의 번호가 90보다 큰 게시물들을 게시물 번호를 기준으로 내림차순으로 검색
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(90L);
		
		results.forEach(board->System.out.println(board));
	}

	@Disabled
	@Test
	void testBnoOrderByPaging() {
		// 게시물의 번호가 90보다 큰 게시물들을 게시물 번호를 기준으로 내림차순으로 검색
		// param1 : 페이지번호(0부터 시작)
		// param2 : 페이지당 데이터 수
		Pageable paging = PageRequest.of(0, 10);
				
		Collection<Board> results = repo.findByBnoGreaterThanOrderByBnoDesc(90L, paging);
				
		results.forEach(board->System.out.println(board));
	}

//	@Disabled
//	@Test
//	void testBnoPagingSort() {
//		// 게시물의 번호가 0보다 큰 게시물들을 검색
//		// 한 페이지에 10건의 게시물 페이징 처리
//		// 페이징 처리된 결과에서 게시물 번호를 기준으로 오름차순 정렬 처리
//		
//		Pageable paging = PageRequest.of(0, 10,Sort.Direction.ASC,"bno");
//		
//		Collection<Board> results = repo.findByBnoGreaterThan(0L, paging);
//		
//		results.forEach(board->System.out.println(board));
//	}
	
	@Disabled
	@Test
	void testBnoPagingSort() {
		Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
		
		Page<Board> result = repo.findByBnoGreaterThan(0L, paging);
		
		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(board -> System.out.println(board));
	}

	@Disabled
	@Test
	void testByTitle2() {
		// 게시물 제목에 17이 포함되는 게시물 검색
		repo.findByTitle("17").forEach(board->System.out.println(board));
	}
	
	@Disabled
	@Test
	void testByContent2() {
		// 게시물 내용에 17이 포함되는 게시물 검색
		repo.findByContent("17").forEach(board->System.out.println(board));
	}

	@Disabled
	@Test
	void testByTitle17() {
		// 게시물의 제목이 17이 포함되는 게시물 검색
		// 필요한 컬럼만 추출하는 테스트, content 칼럼의 내용을 제외하고 검색
		repo.findByTitle2("17").forEach(arr->System.out.println(Arrays.toString(arr)));		
	}
	
	@Disabled
	@Test
	void testByTitle20() {
		// 게시물의 제목이 20이 포함되는 게시물 검색
		// nativeQuery 사용 테스트
		repo.findByTitle3("20").forEach(arr->System.out.println(Arrays.toString(arr)));
	}
	
	@Disabled
	@Test
	void testByPaging() {
		// 한페이지에 10건의 데이터 페이징 처리하여 전체 게시물 검색
		Pageable paging = PageRequest.of(0, 10);
		
		repo.findBypage(paging).forEach(board->System.out.println(board));	
	}

//	@Disabled
	@Test
	void testPredicate() {
		String type = "t";
		String keyword = "17";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard board = QBoard.board;
		
		if(type.equals("t"))
		{
			builder.and(board.title.like("%"+keyword+"%"));
		}
		
		// bno>0
		builder.and(board.bno.gt(0));
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Board> result = repo.findAll(builder, pageable);
		
		System.out.println("PAGE SIZE: " + result.getSize());
		System.out.println("TOTAL PAGES: " + result.getTotalPages());
		System.out.println("TOTAL COUNT: " + result.getTotalElements());
		System.out.println("NEXT: " + result.nextPageable());
		
		List<Board> list = result.getContent();
		
		list.forEach(b -> System.out.println(b));		
	}
}
