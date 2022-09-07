package org.zerock.persistence;

import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.Board;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board>{
		
	// 게시물에서 title 제목의 모든 게시물 검색
	public List<Board> findBoardByTitle(String title);
	
	// 게시물에서 writer 작성자의 모든 게시물 검색
	public Collection<Board> findByWriter(String writer);

	// 게시물 작성자에 writer 문자열이 포함되는 모든 게시물을 검색
	// writer LIKE % ? %
	public Collection<Board> findByWriterContaining(String writer);

	// 게시물의 제목에 title 문자가 포함되거나 내용에 content 문자가 포함되는 모든 게시물 검색
	// title LIKE % ? % OR content LIKE % ? %
	public Collection<Board> findByTitleContainingOrContentContaining(String title, String content);
	
	// 게시물의 제목에 title 문자가 포함되어 있고 게시물 번호가 특정 숫자 이상인 게시물 검색
	// title LIKE % ? % AND BNO > ?
	public Collection<Board> findByTitleContainingAndBnoGreaterThan(String keyword, long num);
	
	// 게시물의 번호가 특정 숫자 이상인 게시물을 게시물 번호를 기준으로 내림차순으로 검색
	// bno > ? ORDER BY bno DESC
	public Collection<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);
	
	// 게시물의 번호가 특정 숫자 이상인 게시물을 게시물 번호를 기준으로 내림차순으로 검색
	// 페이징 처리 
	// bno > ? ORDER BY bno DESC limit ?, ?
	public List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno, Pageable paging);
	
	// 게시물의 번호가 특정 숫자 이상인 게시물을 검색
	// order by절을 생략하고 정렬과 페이징 관련 부분을 Pageable 인터페이스가 처리 가능
//	public List<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	// 객체타입이 Page<T>이면 getContent() 메서드를 통해서 List<T>를 참조할 수 있음
	public Page<Board> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// b.bno > 0 조건으로 인해서 탐색 성능 높힘
	// 쿼리문에서 ?은 JDBC의 PreparedStatment 역할
	// LIKE 문에서 1은 첫번재 파라미터라는 의미, title 문자열이 LIKE문에 삽입됨
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByTitle(String title);

	// 게시물의 내용 검색
	// 내용에 대한 검색 처리 - @Param
	// @Param("content")로 설정된 content 문자열이 @Query 애노테이션의 LIKE문 :content에 매핑 됨
	@Query("SELECT b FROM Board b WHERE b.title LIKE %:content% AND b.bno > 0 ORDER BY b.bno DESC")
	public List<Board> findByContent(@Param("content") String content);

	// 게시물의 제목 검색
	// 필요한 컬럼만 추출하는 경우 사용
	@Query("SELECT b.bno, b.title, b.writer, b.regdate FROM Board b WHERE b.title LIKE %?1% "
			+ "AND b.bno>0 ORDER BY b.bno DESC")
	public List<Object[]> findByTitle2(String title);

	// 게시물의 제목 검색
	// nativeQuery 사용
	// nativeQuery 사용시(nativeQuery=true) @Query의 value값을 그대로 실행함
	@Query(value = "select bno, title, writer from tbl_boards where title like CONCAT('%',?1,'%') "
			+ " and bno>0 order by bno desc",nativeQuery = true)
	public List<Object[]> findByTitle3(String title);
	
	// 게시물 전체 검색
	// @Query와 Paging 처리/정렬
	// @Query로 작성한 내용 + 페이징 처리, 같이 수행 가능
	@Query("SELECT b FROM Board b WHERE b.bno>0 ORDER BY b.bno DESC")
	public List<Board> findBypage(Pageable pageable);
	
	
}
