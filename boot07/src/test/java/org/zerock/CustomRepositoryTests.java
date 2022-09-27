package org.zerock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.persistence.CustomCrudRepository;

import java.util.Arrays;

@SpringBootTest
@Commit
public class CustomRepositoryTests {
    @Autowired
    CustomCrudRepository repo;

    @Test
    public void test1(){
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
        String type = "t";
        String keyword = "10";
        //when
        Page<Object[]> result = repo.getCustomPage(type, keyword, pageable);

        //then
        System.out.println(result);
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL SIZE  : " + result.getTotalElements());
        result.getContent().forEach(arr->System.out.println(Arrays.toString(arr)));
    }

    @Test
    public void testWriter(){
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "bno");
        String type = "w";
        String keyword = "user09";
        //when
        Page<Object[]> result = repo.getCustomPage(type, keyword, pageable);
        //then
        System.out.println(result);
        System.out.println("TOTAL PAGES : " + result.getTotalPages());
        System.out.println("TOTAL SIZE  : " + result.getTotalElements());
        result.getContent().forEach(arr->System.out.println(Arrays.toString(arr)));
    }
}
