package com.id.repository;

import com.id.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<K extends Serializable, E extends BaseEntity<K>> {

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    Optional<E> findById(K id);

    List<E> findAll();
}
