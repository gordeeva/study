package com.sam.app.i;

import java.util.List;

/**
 * Provides support for Create/Read/Update/Delete operations for any entities
 * 
 */
public interface ICRUD<T> {
	
	/**
	 * Creates new entity
	 * @param t entity to create
	 * @return id of new record
	 */
	long create(T t);
	
	/**
	 * Returns all entities
	 * @return entities
	 */
	List<T> getAll();
	
	/**
	 * Gets entity
	 * @param id
	 * @return entity
	 */
	T get(long id);
	
	/**
	 * Updates entity
	 * @param entity to update
	 */
	void update(T t);
	
	/**
	 * Deletes entity
	 * @param id
	 */
	void delete(long id);
}
