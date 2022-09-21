package org.zerock.persistence;

import groovy.util.logging.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log
@Commit
class WebReplyRepositoryTest {

    @Autowired
    private WebReplyRepository webReplyRepository;

    @Test
    public void testInsertReplies(){
        //given
        Long[] arr = {299L, 300L, 303L};
        //when
        Arrays.stream(arr).forEach(num->{
            WebBoard board = WebBoard.builder()
                                     .bno(num)
                                     .build();

            IntStream.range(0, 10).forEach(i->{
                WebReply reply = WebReply.builder()
                                         .replyText("REPLY ..." + i)
                                         .replyer("replyer" + i)
                                         .board(board)
                                         .build();

                webReplyRepository.save(reply);
            });
        });
        //then
    }
}