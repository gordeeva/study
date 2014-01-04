/**
 * 
 */
package com.sam.app.servlet;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;

import com.sam.app.i.ICRUD;


public abstract class AbstractCRUDServlet<T> extends HttpServlet implements
		ICRUD<T> {

	private static final long serialVersionUID = 3542484734046589300L;
	

	protected static final String READ_ACTION = "get";

	protected static final String DELETE_ACTION = "delete";

	@Override
	public long create(T t) {
		getLogger().info("Create of " + t + " was called");
		return -1;
	}
	
		

	@Override
	public List<T> getAll() {
		getLogger().info("GetAll() was called");
		return Collections.emptyList();
	}



	@Override
	public T get(long id) {
		getLogger().info(String.format("Get(%d)  was called", id));
		return null;
	}



	@Override
	public void update(T t) {
		getLogger().info(String.format("Update(%s) was called", t));
	}



	@Override
	public void delete(long id) {
		getLogger().info(String.format("Delete(%d) was called", id));
	}


	abstract protected Logger getLogger();

}
