package com.sam.app.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sam.app.dao.DataController;
import com.sam.app.dao.EmployeeDAO;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import com.sam.app.util.EntityManagerUtil;

@WebServlet(urlPatterns={"/EmployeeServlet"})
public class EmployeeServlet extends AbstractCRUDServlet<Employee> {

	private static final long serialVersionUID = 3918551416797741287L;

	private static Logger logger = LoggerFactory
			.getLogger(EmployeeServlet.class);

	private static final String ADD_ROLE_ACTION = "addRole";

	private static final String DELETE_ROLE_ACTION = "deleteRole";

	private static final String EMPLOYEE_VIEW_NAME = "/employee.jsp";

	private EmployeeDAO employeeDAO;

	private DataController dataController;

	// @PersistenceContext
	private EntityManager em;

	@Override
	public void init() throws ServletException {
		super.init();
		Object connectionObj = getServletContext().getAttribute("dbconnection");
		if (!(connectionObj instanceof Connection)) {
			logger.error((String.format(
					"Can't init EmployeeDAO. DBConnection param is wrong %s",
					connectionObj)));
		}
		dataController = (DataController) getServletContext().getAttribute(
				"dataController");
		employeeDAO = dataController.getEmployeeDAO();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String action = req.getParameter("action");
		action = action == null ? "" : action;
		if (action.equals(READ_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			req.setAttribute("employees", get(id));
		} else if (action.equals(DELETE_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			delete(id);
			req.setAttribute("employees", getAll());
		} else {
			req.setAttribute("employees", getAll());
		}
		RequestDispatcher requestDispatcher = req
				.getRequestDispatcher(EMPLOYEE_VIEW_NAME);
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doGet() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String action = req.getParameter("action");
		action = action == null ? "" : action;
		if (action.equals(ADD_ROLE_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			String roleName = req.getParameter("roleName");
			System.out.println("role: " + roleName);
			addRole(id, roleName);
		} else if (action.equals(DELETE_ROLE_ACTION)) {
			long id = Long.valueOf(req.getParameter("id"));
			String roleName = req.getParameter("roleName");
			deleteRole(id, roleName);
		} else { // create or update employee
			Employee emp = new Employee();
			String name = req.getParameter("name");
			emp.setName(name);
			String id = req.getParameter("id");
			// if (id == null || id.isEmpty()) {
			create(emp);
			// }
			// else {
			// emp.setId(Long.valueOf(id));
			// update(emp);
			// }
		}

		req.setAttribute("employees", getAll());
		RequestDispatcher requestDispatcher = req
				.getRequestDispatcher(EMPLOYEE_VIEW_NAME);
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException e) {
			logger.error("doPost() failed", e);
		} catch (IOException e) {
		}
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public long create(Employee employee) {
		// super.create(employee);
		// return employeeDAO.create(employee);
		// return HibernateUtil.saveEmployee(employee);
		return EntityManagerUtil.saveEmployeeEM(employee);
		// return HibernateUtil.saveEmployeeEM(employee, em);
	}

	@Override
	public List<Employee> getAll() {
		super.getAll();
		return employeeDAO.getAll();
		// return null;
	}

	@Override
	public Employee get(long id) {
		super.get(id);
		return employeeDAO.get(id);
		// return null;
	}

	@Override
	public void update(Employee employee) {
		// super.update(employee);
		// employeeDAO.update(employee);
	}

	@Override
	public void delete(long id) {
		// super.delete(id);
		// employeeDAO.delete(id);
	}

	private void addRole(long empId, String roleName) {
		logger.info("Add role was called");
		Long roleId = findRoleId(roleName);
		if (roleId != null) {
			employeeDAO.addRole(empId, roleId);
		} else {
			logger.warn(String.format("Role Id for name %s was not found",
					roleName));
		}
	}

	private void deleteRole(long empId, String roleName) {
		logger.info("Delete role was called");
		Long roleId = findRoleId(roleName);
		if (roleId != null) {
			employeeDAO.deleteRole(empId, roleId);
		} else {
			logger.warn(String.format("Role Id for name %s was not found",
					roleName));
		}
	}

	private Long findRoleId(String roleName) {
		List<Role> roles = dataController.getRoles();
		for (Role role : roles) {
			if (role.getName().equals(roleName)) {
				return role.getId();
			}
		}
		return null;
	}

}
