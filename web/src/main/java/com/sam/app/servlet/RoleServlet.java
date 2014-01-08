package com.sam.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.dao.DataController;
import com.sam.app.dao.RoleDAO;
import com.sam.app.model.Employee;
import com.sam.app.model.Role;

public class RoleServlet extends AbstractCRUDServlet<Role> {

	private static final Logger logger = LoggerFactory
			.getLogger(RoleServlet.class);

	private static final long serialVersionUID = 1454943414322885268L;

	private static final String ROLE_VIEW_NAME = "/role.jsp";

	private RoleDAO roleDAO;

	private DataController dataController;

	@Override
	public void init() throws ServletException {
		Object connectionObj = getServletContext().getAttribute("dbconnection");
		if (!(connectionObj instanceof Connection)) {
			logger.error((String.format(
					"Can't init roleDAO. DBConnection param is wrong %s",
					connectionObj)));
		}

		dataController = (DataController) getServletContext().getAttribute(
				"dataController");
		roleDAO = dataController.getRoleDAO();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String action = req.getParameter("action") == null ? "" : req
				.getParameter("action");
		if (action.equals(READ_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			req.setAttribute("roles", get(id));
		} else if (action.equals(DELETE_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			delete(id);
			req.setAttribute("roles", getAll());
		} else {
			req.setAttribute("roles", getAll());
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher(ROLE_VIEW_NAME);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doGet() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("name" + req.getParameter("name"));
		Role role = new Role();
		role.setName(req.getParameter("name"));
		String id = req.getParameter("id");
		if (id == null || id.isEmpty()) {
			create(role);
		} else {
			role.setId(Long.valueOf(id));
			update(role);
		}
		req.setAttribute("roles", getAll());
		RequestDispatcher dispatcher = req.getRequestDispatcher(ROLE_VIEW_NAME);
		try {
			dispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doPost() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	public long create(Role role) {
		super.create(role);
		return roleDAO.create(role);
	}

	@Override
	public List<Role> getAll() {
		super.getAll();
		return roleDAO.getAll();
	}

	@Override
	public Role get(long id) {
		super.get(id);
		return roleDAO.get(id);
	}

	@Override
	public void update(Role role) {
		super.update(role);
		roleDAO.update(role);
	}

	@Override
	public void delete(long id) {
		super.delete(id);
		roleDAO.delete(id);
		for (Employee emp : dataController.getEmployeeByRole(id)) {
			// emp.removeRole(id);
		}
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
