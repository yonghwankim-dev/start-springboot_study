package org.zerock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebBoardRepository;
import org.zerock.persistence.WebReplyRepository;

@RestController
@RequestMapping("/replies/*")
@RequiredArgsConstructor
@Log
public class WebReplyController {
    private final WebReplyRepository webReplyRepository;

    @PostMapping("/{bno}")
    public ResponseEntity<Void> addReply(@PathVariable("bno") Long bno, @RequestBody WebReply reply){
        log.info("addReply.......");
        log.info("BNO   : " + bno);
        log.info("REPLY : " + reply);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
