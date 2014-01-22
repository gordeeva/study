package com.sam.app.servlet;

import com.sam.app.util.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory
      .getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AbstractEntityService.initEMF();
        LOGGER.info("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbstractEntityService.closeEMF();
        LOGGER.info("contextDestroyed");
    }

}
