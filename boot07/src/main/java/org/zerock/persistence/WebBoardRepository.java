package org.zerock.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.WebBoard;

import java.util.List;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>
        , QuerydslPredicateExecutor<WebBoard> {

    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QWebBoard board = QWebBoard.webBoard;

        // type if ~ else

        // bno > 0
        builder.and(board.bno.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("t")){
            builder.and(board.title.like("%"+keyword+"%"));
        }else if(type.equals("c")){
            builder.and(board.content.like("%"+keyword+"%"));
        }else if(type.equals("w")){
            builder.and(board.writer.like("%"+keyword+"%"));
        }

        return builder;
    }

    @Query("SELECT b.bno, b.title, b.writer, b.regdate, count(r) FROM WebBoard b" +
            " LEFT OUTER JOIN b.replies r WHERE b.bno > 0 GROUP BY b")
    List<Object[]> getListWithQuery(Pageable page);
}
