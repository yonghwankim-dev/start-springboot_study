package org.zerock.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyReopository;

import lombok.extern.java.Log;

@RestController
@RequestMapping("/replies/*")
@Log
public class WebReplyController {
	
	@Autowired
	private WebReplyReopository replyRepo;

	// 댓글 추가
	// @PathVariable : URL의 일부를 파라미터로 받기 위해서 사용함
	// @RequestBody : JSON으로 전달되는 데이터를 객체로 자동으로 변환되도록 처리하는 역할
	@Transactional	// save(), findBoard() 연속호출로 인하여 트랜잭션 처리 수행
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> addReply(
			@PathVariable("bno") Long bno,
			@RequestBody WebReply reply){
		log.info("addReply.......................");
		log.info("BNO: " + bno);
		log.info("REPLY: " + reply);
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		reply.setBoard(board);
		
		replyRepo.save(reply);
		
		// ResponseEntity는 코드를 이용해서 직접 Http Response의 상태코드와
		// 데이터를 직접 제어해서 처리할 수 있음
		return new ResponseEntity<>(getListByBoard(board),HttpStatus.CREATED);
	}
	
	// 댓글 삭제
	@Transactional
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> remove(
			@PathVariable("bno") Long bno,
			@PathVariable("rno") Long rno){
		log.info("delete reply : " + rno);
		
		replyRepo.deleteById(rno);
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		
		
		return new ResponseEntity<>(getListByBoard(board),HttpStatus.OK);
	}
	
	// 댓글 수정
	@Transactional
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> modify(
			@PathVariable("bno") Long bno,
			@RequestBody WebReply reply){
		log.info("modify reply : " + reply);
		
		replyRepo.findById(reply.getRno()).ifPresent(origin->{
			origin.setReplyText(reply.getReplyText());
			replyRepo.save(origin);
		});
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
			
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
	}
	
	// 댓글 목록, 게시물에 따른 댓글 목록 리스트 반환
	@GetMapping("/{bno}")
	public ResponseEntity<List<WebReply>> getReplies(
			@PathVariable("bno") Long bno){
		log.info("get All Replies............................");
		
		WebBoard board = new WebBoard();
		board.setBno(bno);
		return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
	}
	
	
	private List<WebReply> getListByBoard(WebBoard board){
		log.info("getListByBoard..."+board);
		
		return replyRepo.getRepliesOfBoard(board);
	}
	
}
