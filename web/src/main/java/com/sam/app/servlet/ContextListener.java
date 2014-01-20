package com.sam.app.servlet;

import com.sam.app.util.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.AbstractExecutorService;


@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
        initDAO();
    }

    private void initDAO() {
        try {
            Class.forName("com.sam.app.util.AbstractEntityService");
        } catch (ClassNotFoundException e) {
            logger.error("DAO initialization failed");
        }
    }

    @Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
	
}
