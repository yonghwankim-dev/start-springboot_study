package org.zerock.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.QWebBoard;
import org.zerock.domain.WebBoard;

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
}
