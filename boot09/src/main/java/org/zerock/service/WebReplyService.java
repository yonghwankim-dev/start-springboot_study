package org.zerock.service;

import groovy.util.logging.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.domain.WebReply;
import org.zerock.persistence.WebReplyRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class WebReplyService {
    private final WebReplyRepository webReplyRepository;

    public <S extends WebReply> S save(S entity) {
        return webReplyRepository.save(entity);
    }

    public <S extends WebReply> Iterable<S> saveAll(Iterable<S> entities) {
        return webReplyRepository.saveAll(entities);
    }

    public Optional<WebReply> findById(Long aLong) {
        return webReplyRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return webReplyRepository.existsById(aLong);
    }

    public Iterable<WebReply> findAll() {
        return webReplyRepository.findAll();
    }

    public Iterable<WebReply> findAllById(Iterable<Long> longs) {
        return webReplyRepository.findAllById(longs);
    }

    public long count() {
        return webReplyRepository.count();
    }

    public void deleteById(Long aLong) {
        webReplyRepository.deleteById(aLong);
    }

    public void delete(WebReply entity) {
        webReplyRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        webReplyRepository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends WebReply> entities) {
        webReplyRepository.deleteAll(entities);
    }

    public void deleteAll() {
        webReplyRepository.deleteAll();
    }
}
