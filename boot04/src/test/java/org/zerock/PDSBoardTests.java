package org.zerock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.PDSBoard;
import org.zerock.domain.PDSFile;
import org.zerock.persistence.PDSBoardRepository;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
class PDSBoardTests {
	@Autowired
	PDSBoardRepository repo;

	// 1개의 자료와 2개의 첨부파일을 저장하려고 시도
	@Disabled
	@Test
	void testInsertPDS() {
		PDSBoard pds = new PDSBoard();
		pds.setPname("DOCUMENT 1 - 2");
		
		PDSFile file1 = new PDSFile();
		file1.setPdsfile("file1.doc");
		
		PDSFile file2 = new PDSFile();
		file2.setPdsfile("file2.doc");
		
		List<PDSFile> list = new ArrayList<PDSFile>();
		list.add(file1);
		list.add(file2);
		
		pds.setFiles(list);
		
		log.info("trye to save pds");
		repo.save(pds);
	}

	// 1번파일번호의 첨부파일명을 변경
	@Disabled
	@Transactional
	@Test
	public void testUpdateFileName1() {
		Long fno = 1L;
		String newName = "updatedFile1.doc";
		
		int count = repo.updatePDSFile(fno, newName);
		// @Log 설정된 이후 사용 가능
		log.info("update count: " + count);
	}
	
	// pid가 2인 PDSBoard 객체를 얻고 fno가 2인 첨부파일명을 변경한다.
	@Disabled
	@Transactional
	@Test
	public void testUpdateFileName2() {
		String newName = "updatedFile2.doc";
		
		// 반드시 번호가 존재하는지 확인할 것
		Optional<PDSBoard> result = repo.findById(2L);
		
		result.ifPresent(pds->{
			log.info("데이터가 존재하므로 update 시도");
			PDSFile target = new PDSFile();
			target.setFno(2L);
			target.setPdsfile(newName);
			
			int idx = pds.getFiles().indexOf(target);
			
			if(idx > -1) {
				List<PDSFile> list = pds.getFiles();
				list.remove(idx);
				list.add(target);
				pds.setFiles(list);
			}
			
			repo.save(pds);
		});
	}
	
	// 파일번호에 따른 첨부파일을 삭제
	@Disabled
	@Transactional
	@Test
	public void deletePDSFile() {
		// 첨부 파일 번호
		Long fno = 2L;
		
		int count = repo.deletePDSFile(fno);
		
		log.info("DELETE PDSFILE : " + count);
	}
	
	// 샘플 데이터 추가
	@Disabled
	@Test
	public void insertDummies() {
		List<PDSBoard> list = new ArrayList<PDSBoard>();
		
		IntStream.range(1, 100).forEach(i->{
			PDSBoard pds = new PDSBoard();
			pds.setPname("자료 " + i);
			
			PDSFile file1 = new PDSFile();
			file1.setPdsfile("file1.doc");
			
			PDSFile file2 = new PDSFile();
			file2.setPdsfile("file2.doc");
			
			List<PDSFile> pdsfile_list = new ArrayList<>();
			pdsfile_list.add(file1);
			pdsfile_list.add(file2);
			pds.setFiles(pdsfile_list);
			
			log.info("try to save pds");
			
			list.add(pds);
			
		});
		
		repo.saveAll(list);
	}
	
	// 자료와 첨부 파일의 개수를 자료번호의 역순으로 출력
//	@Disabled
	@Test
	public void viewSummary() {
		repo.getSummary().forEach(arr -> log.info(Arrays.toString(arr)));
	}
}
