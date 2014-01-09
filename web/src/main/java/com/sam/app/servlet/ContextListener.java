package com.sam.app.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sam.app.dao.DataController;
import com.sam.app.dao.DataController.DataControllerBuilder;
import com.sam.app.dao.jdbc.DepartmentDAO;
import com.sam.app.dao.jdbc.EmployeeDAOImpl;
import com.sam.app.dao.jdbc.RoleDAO;

//@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

//		Connection connection = createConnection(sce.getServletContext());
//		sce.getServletContext().setAttribute("dbconnection", connection);
//		
//		initDAO(connection, sce.getServletContext());
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
