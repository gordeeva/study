package com.sam.app.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.dao.DataController;
import com.sam.app.dao.jdbc.DepartmentDAO;
import com.sam.app.domain.Department;

public class DepartmentServlet extends AbstractCRUDServlet<Department> {

	private static final long serialVersionUID = 5763031550113580414L;

	private static final Logger logger = LoggerFactory
			.getLogger(DepartmentServlet.class);

	private static final String DEPARTMENT_VIEW_NAME = "/department.jsp";

	private DepartmentDAO departmentDAO;

	@Override
	public void init() throws ServletException {
		super.init();
		Object connectionObj = getServletContext().getAttribute("dbconnection");
		if (!(connectionObj instanceof Connection)) {
			logger.error("Retreiving db connection failed: got {} here",
					connectionObj);
		}

		DataController dataController = (DataController) getServletContext()
				.getAttribute("dataController");
		departmentDAO = dataController.getDepartmentDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String action = req.getParameter("action") == null ? "" : req
				.getParameter("action");
		System.out.println("action: " + action);
		if (action.equals(READ_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			req.setAttribute("departments", get(id));
		} else if (action.equals(DELETE_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			delete(id);
			req.setAttribute("departments", getAll());
		} else if (action.equals("locale")) {
			String lang = req.getParameter("lang");
			lang = lang == null ? "" : lang;
			System.out.println("lang: " + lang);
			Locale locale;
			if (lang.equals("en")) {
				locale = new Locale("en", "EN");
				HttpSession session = req.getSession(true);
				session.setAttribute("lang", locale);
			} else if (lang.equals("ru")) {
				locale = new Locale("ru", "RU");
				HttpSession session = req.getSession(true);
				session.setAttribute("lang", locale);
			} else {
				locale = req.getLocale();
				HttpSession session = req.getSession(true);
				session.setAttribute("lang", locale);
			}
			System.out.println("set lang: "
					+ req.getSession().getAttribute("lang"));
			req.setAttribute("departments", getAll());
		} else {
			req.setAttribute("departments", getAll());
		}
		RequestDispatcher dispatcher = req
				.getRequestDispatcher(DEPARTMENT_VIEW_NAME);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doGet() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws UnsupportedEncodingException {
		Department department = new Department();
		department.setName(req.getParameter("name"));
		String id = req.getParameter("id");
		if (id == null || id.isEmpty()) {
			create(department);
		} else {
			department.setId(Long.valueOf(id));
			update(department);
		}
		req.setAttribute("departments", getAll());
		RequestDispatcher dispatcher = req
				.getRequestDispatcher(DEPARTMENT_VIEW_NAME);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doPost() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	public long create(Department department) {
		super.create(department);
		return departmentDAO.create(department);
	}

	@Override
	public List<Department> getAll() {
		super.getAll();
		return departmentDAO.getAll();
	}

	@Override
	public Department get(long id) {
		super.get(id);
		return departmentDAO.get(id);
	}

	@Override
	public void update(Department department) {
		super.update(department);
		departmentDAO.update(department);
	}

	@Override
	public void delete(long id) {
		super.delete(id);
		departmentDAO.delete(id);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
