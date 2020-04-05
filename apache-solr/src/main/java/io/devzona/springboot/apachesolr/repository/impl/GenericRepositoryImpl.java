package io.devzona.springboot.apachesolr.repository.impl;

import io.devzona.springboot.apachesolr.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Transactional
public abstract class GenericRepositoryImpl<T, ID extends Serializable> implements GenericRepository<T, ID> {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> clz;

    public GenericRepositoryImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public <S extends T> S save(S entity) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll() {
        return null;
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ID id) {

    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Iterable<T> findByQuery(String queryString, Map<String, Object> params) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public T getOne(ID id) {
        return null;
    }
}
