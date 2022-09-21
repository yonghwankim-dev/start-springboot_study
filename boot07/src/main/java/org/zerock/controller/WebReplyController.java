package org.zerock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.persistence.WebReplyRepository;

import java.util.List;

@RestController
@RequestMapping("/replies/*")
@RequiredArgsConstructor
@Log
public class WebReplyController {
    private final WebReplyRepository webReplyRepository;

    @Transactional
    @PostMapping("/{bno}")
    public ResponseEntity<List<WebReply>> addReply(@PathVariable("bno") Long bno, @RequestBody WebReply reply){
        log.info("addReply.......");
        log.info("BNO   : " + bno);
        log.info("REPLY : " + reply);

        WebBoard board = WebBoard.builder()
                                 .bno(bno)
                                 .build();

        reply.setBoard(board);
        webReplyRepository.save(reply);
        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }

    private List<WebReply> getListByBoard(WebBoard board) {
        log.info("getListByBoard..." + board);
        return webReplyRepository.getRepliesOfBoard(board);
    }

    @Transactional
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<WebReply>> deleteReply(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno){
        log.info("delete reply: " + rno);

        webReplyRepository.deleteById(rno);

        WebBoard board = WebBoard.builder()
                                 .bno(bno)
                                 .build();

        return new ResponseEntity<>(getListByBoard(board), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{bno}")
    public ResponseEntity<List<WebReply>> modifyReply(@PathVariable("bno") Long bno, @RequestBody WebReply reply){
        log.info("modify reply: " + reply);

        webReplyRepository.findById(reply.getRno()).ifPresent(origin->{
            origin.setReplyText(reply.getReplyText());
            webReplyRepository.save(origin);
        });

        WebBoard board = WebBoard.builder().bno(bno).build();
        return new ResponseEntity<>(getListByBoard(board), HttpStatus.CREATED);
    }



}
