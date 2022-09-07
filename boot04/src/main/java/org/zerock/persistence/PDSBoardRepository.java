package org.zerock.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long>{
	
	// 파일번호에 따른 첨부파일명을 변경
	@Modifying
	@Query("UPDATE FROM PDSFile f set f.pdsfile = ?2 WHERE f.fno= ?1")
	public int updatePDSFile(Long fno, String newFileName);

	// 파일번호에 따른 첨부파일 삭제
	@Modifying
	@Query("DELETE FROM PDSFile f where f.fno = ?1")
	public int deletePDSFile(Long fno);

	// 자료와 첨부 파일의 수를 자료 번호의 역순으로 출력
	@Query("SELECT p, count(f) FROM PDSBoard p LEFT OUTER JOIN p.files f "
			+ " ON p.pid = f WHERE p.pid > 0 GROUP BY p ORDER BY p.pid DESC ")
	public List<Object[]> getSummary();
}
