package org.zerock.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zerock.domain.WebBoard;
import org.zerock.domain.WebReply;

import java.util.List;

public interface WebReplyRepository extends CrudRepository<WebReply, Long> {

    @Query("SELECT r FROM WebReply r WHERE r.board = :board AND r.rno > 0 ORDER BY r.rno ASC")
    List<WebReply> getRepliesOfBoard(@Param("board") WebBoard board);
}
