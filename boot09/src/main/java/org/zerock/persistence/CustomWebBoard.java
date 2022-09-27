package org.zerock.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 1. 사용자 정의 인터페이스 설계
public interface CustomWebBoard {
    Page<Object[]> getCustomPage(String type, String keyword, Pageable page);
}
