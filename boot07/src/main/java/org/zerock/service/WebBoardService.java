package org.zerock.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import groovy.util.logging.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.zerock.domain.WebBoard;
import org.zerock.persistence.WebBoardRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log
public class WebBoardService {
    private final WebBoardRepository repo;

    public Predicate makePredicates(String type, String keyword) {
        return repo.makePredicates(type, keyword);
    }

    public <S extends WebBoard> S save(S entity) {
        return repo.save(entity);
    }

    public <S extends WebBoard> Iterable<S> saveAll(Iterable<S> entities) {
        return repo.saveAll(entities);
    }

    public Optional<WebBoard> findById(Long aLong) {
        return repo.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return repo.existsById(aLong);
    }

    public Iterable<WebBoard> findAll() {
        return repo.findAll();
    }

    public Iterable<WebBoard> findAllById(Iterable<Long> longs) {
        return repo.findAllById(longs);
    }

    public long count() {
        return repo.count();
    }

    public void deleteById(Long aLong) {
        repo.deleteById(aLong);
    }

    public void delete(WebBoard entity) {
        repo.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        repo.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends WebBoard> entities) {
        repo.deleteAll(entities);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public Optional<WebBoard> findOne(Predicate predicate) {
        return repo.findOne(predicate);
    }

    public Iterable<WebBoard> findAll(Predicate predicate) {
        return repo.findAll(predicate);
    }

    public Iterable<WebBoard> findAll(Predicate predicate, Sort sort) {
        return repo.findAll(predicate, sort);
    }

    public Iterable<WebBoard> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return repo.findAll(predicate, orders);
    }

    public Iterable<WebBoard> findAll(OrderSpecifier<?>... orders) {
        return repo.findAll(orders);
    }

    public Page<WebBoard> findAll(Predicate predicate, Pageable pageable) {
        return repo.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return repo.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return repo.exists(predicate);
    }

    public <S extends WebBoard, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return repo.findBy(predicate, queryFunction);
    }
}
