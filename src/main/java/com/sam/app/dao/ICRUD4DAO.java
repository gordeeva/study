package com.sam.app.dao;

import java.util.List;

public interface ICRUD4DAO<T> {

	List<T> getAll();
	
	T get(long id);
	
	Long create(T t);
	
	void update(T t);
	
	void delete(long id);

}
