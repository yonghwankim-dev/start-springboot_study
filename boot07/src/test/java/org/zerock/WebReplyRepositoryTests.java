package org.zerock;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyReopository;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
public class WebReplyRepositoryTests {
	@Autowired
	WebReplyReopository repo;
	
	@Test
	public void testInsertReplies() {
		Long[] arr = {298L,299L,300L};
		
		Arrays.stream(arr).forEach(num->{
			WebBoard board = new WebBoard();
			board.setBno(num);
			
			IntStream.range(0, 10).forEach(i->{
				WebReply reply = new WebReply();
				reply.setReplyText("REPLY... "+i);
				reply.setReplyer("replyer"+i);
				reply.setBoard(board);
				
				repo.save(reply);
			});
		});
	}
}
