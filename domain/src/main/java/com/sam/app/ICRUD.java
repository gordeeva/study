package com.sam.app;

import java.util.List;

/**
 * Provides support for Create/Read/Update/Delete operations for any entities
 */
public interface ICRUD<T> {

    /**
     * Creates new entity
     *
     * @param t entity to create
     * @return id of new record
     */
	Long create(T t);

    /**
     * Returns all entities
     *
     * @return entities
     */
    List<T> getAll();

    /**
     * Gets entity
     *
     * @param id
     * @return entity
     */
    T get(Long id);

    /**
     * Updates entity
     *
     * @param entity to update
     */
    T update(T t);

    /**
     * Deletes entity
     *
     * @param id
     */
	T delete(Long id);
}
