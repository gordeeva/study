package com.sam.app.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


//@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
	
}
