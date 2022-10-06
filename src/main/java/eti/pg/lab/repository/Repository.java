package eti.pg.lab.repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <E> - type of entity
 * @param <K> - type of primary key
 */

public interface Repository<E,K> {

    /**
     * find an object with primary key
     * @param id - primary key
     * @return - returns a copy of the object if found, else empty optional
     */
    Optional<E> find(K id);

    /**
     * find all objects
     * @return - list of all entites, can be null if none exist
     */
    List<E> findAll();

    /**
     * create a new entity object
     * @param entity - the entity we want to create
     */
    void create(E entity);

    /**
     * delete an entity by its primary key
     * @param id - the primary key
     */
    void delete(K id);

}
