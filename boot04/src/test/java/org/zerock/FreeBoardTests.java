package org.zerock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.FreeBoard;
import org.zerock.domain.FreeBoardReply;
import org.zerock.persistence.FreeBoardReplyRepository;
import org.zerock.persistence.FreeBoardRepository;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
class FreeBoardTests {
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeBoardReplyRepository replyRepo;

	// 게시물 샘플 데이터 생성
	@Disabled
	@Test
	public void insertDummy() {
		IntStream.range(1, 200).forEach(i->{
			FreeBoard board = new FreeBoard();
			
			board.setTitle("Free Board..." +i);
			board.setContent("Free Content..."+i);
			board.setWriter("user"+i%10);
			
			boardRepo.save(board);
		});
	}
	
	// 양방향으로 FreeBoard 객체 얻어온 후 FreeBoardReply를
	// 댓글 리스트에 추가한 후에 FreeBoard 자체를 저장하는 방식
	@Disabled
	@Transactional
	@Test
	public void insertReply2Way() {
		Optional<FreeBoard> result = boardRepo.findById(199L);
		
		result.ifPresent(board ->{
			List<FreeBoardReply> replies = board.getReplies();
			FreeBoardReply reply = new FreeBoardReply();
			reply.setReply("REPLY...");
			reply.setReplyer("replyer00");
			reply.setBoard(board);
			
			replies.add(reply);
			
			board.setReplies(replies);
			
			boardRepo.save(board);
		});
	}
	
	// 단방향 방식으로 199번 게시물에 댓글을 추가
	@Disabled
	@Test
	public void insertReply1Way() {
		FreeBoard board = new FreeBoard();
		board.setBno(199L);
		
		FreeBoardReply reply = new FreeBoardReply();
		reply.setReply("REPLY...");
		reply.setReplyer("replyer00");
		reply.setBoard(board);
		
		replyRepo.save(reply);
	}
	
	// 200여개의 게시물 데이터를 역순으로 출력하는 테스트
	@Disabled
	@Test
	public void testList1() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC,"bno");
		
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board->{
			log.info(board.getBno() + ": " + board.getTitle());
		});
	}
	
	// testList1 테스트의 내용과 동일하나 게시물 제목 옆에 댓글수를 출력하는 테스트
	@Disabled
	@Transactional
	@Test
	public void testList2() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC,"bno");
		
		boardRepo.findByBnoGreaterThan(0L, page).forEach(board->{
			log.info(board.getBno() + ": " + board.getTitle() +":" +board.getReplies().size());
		});
	}
	
	// 게시물 번호, 제목, 댓글 수를 번호를 기준으로 역순으로 출력하는 테스트
	// 기존 지연로딩의 문제점인 SQL의 과다 조회로 인한 문제점을 @Query와 Fetch Join을 이용하여 해결
//	@Disabled
	@Test
	public void testList3() {
		Pageable page = PageRequest.of(0, 10, Sort.Direction.DESC,"bno");
		boardRepo.getPage(page).forEach(arr -> log.info(Arrays.toString(arr)));		
	}
}
