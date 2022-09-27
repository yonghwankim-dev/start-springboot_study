package org.zerock.persistence;

import org.springframework.data.repository.CrudRepository;
import org.zerock.domain.WebBoard;

// 엔티티의 Repository 인터페이스 설계
public interface CustomCrudRepository extends CrudRepository<WebBoard, Long>, CustomWebBoard {

}
