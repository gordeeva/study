package com.sam.app.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.LoggerFactory;

import com.sam.app.dao.DataController;
import com.sam.app.dao.DataController.DataControllerBuilder;
import com.sam.app.dao.DepartmentDAO;
import com.sam.app.dao.EmployeeDAOImpl;
import com.sam.app.dao.RoleDAO;

//@WebServletContextListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		initLogger(sce.getServletContext());
		
		Connection connection = createConnection(sce.getServletContext());
		sce.getServletContext().setAttribute("dbconnection", connection);
		
		initDAO(connection, sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
	
	private void initDAO(Connection connection, ServletContext servletContext) {
		DataController.DataControllerBuilder builder = new DataControllerBuilder();
		EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl(connection);
		builder.setEmployeeDAO(employeeDAO);
		builder.setDepartmentDAO(new DepartmentDAO(connection));
		builder.setRoleDAO(new RoleDAO(connection));
		DataController dataController = builder.build();
		employeeDAO.setDataController(dataController);
		servletContext.setAttribute("dataController", dataController);
	}

	private void initLogger(ServletContext servletContext) {
		String log4jFile = servletContext.getInitParameter("log4jFileName");
		if (log4jFile == null) {
			System.out.println("No log4j-config init param, initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String log4jProp = System.getProperty("user.home") + "\\samApp\\" + log4jFile;
			try {
				DOMConfigurator.configure(log4jProp);
				System.out.println("configured log4j with filepath: "
						+ log4jProp);
			} catch (Exception e) {
				System.out.println("Can't configure log4j with filepath: "
						+ log4jProp);
			}
		}
		LoggerFactory.getLogger(getClass()).info("log4j configured properly");
	}

	private Connection createConnection(ServletContext servletContext) {
		String driver = servletContext.getInitParameter("dbdriver");
		String url = servletContext.getInitParameter("dburl");
		String user = servletContext.getInitParameter("dbuser");
		String password = servletContext.getInitParameter("dbpassword");
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

}
