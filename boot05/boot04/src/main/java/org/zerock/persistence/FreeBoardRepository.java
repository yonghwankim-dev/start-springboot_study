package org.zerock.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.FreeBoard;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>{
	// 특정 게시물번호보다 큰 게시물 탐색
	public List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	
	// 게시물 번호, 제목, 댓글 개수를 출력합니다.
	@Query("SELECT b.bno, b.title, count(r) "
			+ " FROM FreeBoard b LEFT OUTER JOIN b.replies r "
			+ " WHERE b.bno > 0 GROUP BY b ")
	public List<Object[]> getPage(Pageable page);
}
