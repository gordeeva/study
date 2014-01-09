package com.sam.app.servlet;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;


public abstract class AbstractCRUDServlet<T> extends HttpServlet {

	private static final long serialVersionUID = 3542484734046589300L;

	protected static final String READ_ACTION = "get";

	protected static final String DELETE_ACTION = "delete";

	public long create(T t) {
		getLogger().info("Create of " + t + " was called");
		return -1;
	}

	public List<T> getAll() {
		getLogger().info("GetAll() was called");
		return Collections.emptyList();
	}

	public T get(long id) {
		getLogger().info(String.format("Get(%d)  was called", id));
		return null;
	}

	public void update(T t) {
		getLogger().info(String.format("Update(%s) was called", t));
	}

	public void delete(long id) {
		getLogger().info(String.format("Delete(%d) was called", id));
	}

	abstract protected Logger getLogger();
}
