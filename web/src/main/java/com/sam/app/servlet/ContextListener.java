package com.sam.app.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.util.AbstractEntityService;

@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory
			.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		AbstractEntityService.initEMF();
		logger.info("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		AbstractEntityService.closeEMF();
		logger.info("contextDestroyed");
	}

}
