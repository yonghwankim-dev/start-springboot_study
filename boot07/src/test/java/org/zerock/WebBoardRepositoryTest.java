package org.zerock;



import groovy.util.logging.Log;
import groovy.util.logging.Log4j2;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Commit
public class WebBoardRepositoryTest {
    @Autowired
    WebBoardRepository webBoardRepository;

    @Test
    public void 삽입_샘플데이터(){
        IntStream.rangeClosed(1, 300).forEach(i->{
            WebBoard webBoard = WebBoard.builder()
                    .title("Sample Board Title " + i)
                    .content("Content Sample... " + i + " of Board")
                    .writer("user0" + (i % 10))
                    .build();
            webBoardRepository.save(webBoard);
        });
    }

    @Test
    public void 페이징검색(){
        // 0페이지, 20개, bno 내림차순
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicates(null, null), pageable);

        System.out.println(result.getPageable());
        result.getContent().forEach(System.out::println);
    }

    @Test
    public void 페이징검색_제목(){
        //given
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "bno");
        String type = "t";
        String keyword = "10";
        //when
        Page<WebBoard> result = webBoardRepository.findAll(webBoardRepository.makePredicates(type, keyword), pageable);
        //then
        result.getContent().forEach(System.out::println);
    }

    @Test
    public void 댓글개수가포함된_제목(){
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
        //when
        List<Object[]> list = webBoardRepository.getListWithQuery(pageable);
        //then
        list.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

}