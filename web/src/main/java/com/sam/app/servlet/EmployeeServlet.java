package com.sam.app.servlet;

import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import com.sam.app.util.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = { "/EmployeeServlet" })
public class EmployeeServlet extends AbstractCRUDServlet<Employee> {

    private static final long serialVersionUID = 3918551416797741287L;

    private static Logger LOGGER = LoggerFactory
      .getLogger(EmployeeServlet.class);

    private static final String ADD_ROLE_ACTION = "addRole";

    private static final String DELETE_ROLE_ACTION = "deleteRole";

    private static final String EMPLOYEE_VIEW_NAME = "/employee.jsp";

    private AbstractEntityService<Employee> service = new AbstractEntityService<Employee>(
      Employee.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        action = action == null ? "" : action;
        if (action.equals(READ_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            req.setAttribute("departments", getAllDepartments());
            req.setAttribute("roles", getAllRoles());
            req.setAttribute("employees", get(id));

        } else if (action.equals(DELETE_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            delete(id);
            req.setAttribute("departments", getAllDepartments());
            req.setAttribute("employees", getAll());
            req.setAttribute("roles", getAllRoles());
        } else if (action.equals(LOCALE)) {
            updateLocale(req);
            req.setAttribute("departments", getAllDepartments());
            req.setAttribute("employees", getAllRoles());
            req.setAttribute("roles", getAllRoles());
        } else {
            req.setAttribute("departments", getAllDepartments());
            req.setAttribute("roles", getAllRoles());
            req.setAttribute("employees", getAll());
        }
        RequestDispatcher requestDispatcher = req
          .getRequestDispatcher(EMPLOYEE_VIEW_NAME);
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            LOGGER.error("doGet() failed", e);
        } catch (IOException e) {
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        action = action == null ? "" : action;
        if (action.equals(ADD_ROLE_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            long roleId = Long.valueOf(req.getParameter("new_roles"));
            addRole(id, roleId);
        } else if (action.equals(DELETE_ROLE_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            long roleId = Long.valueOf(req.getParameter("existing_roles"));
            deleteRole(id, roleId);
        } else { // create or update employee
            Employee emp = new Employee();
            String name = req.getParameter("name");
            long departmentId = Long.valueOf(req.getParameter("department"));
            emp.setName(name);
            emp.setDepartment(service.getDepartment(departmentId));
            String id = req.getParameter("id");
            if (id == null || id.isEmpty()) {
                create(emp);
            } else {
                emp.setId(Long.valueOf(id));
                update(emp);
            }
        }

        req.setAttribute("departments", getAllDepartments());
        req.setAttribute("employees", getAll());
        req.setAttribute("roles", getAllRoles());
        RequestDispatcher requestDispatcher = req
          .getRequestDispatcher(EMPLOYEE_VIEW_NAME);
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            LOGGER.error("doPost() failed", e);
        } catch (IOException e) {
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public long create(Employee employee) {
        super.create(employee);
        return service.create(employee);
    }

    @Override
    public List<Employee> getAll() {
        super.getAll();
        List<Employee> allEmployees = service.getAllEmployees();
        setRolesToAddToEmployee(allEmployees);
        return allEmployees;
    }

    private void setRolesToAddToEmployee(List<Employee> employees) {
        for (Employee employee : employees) {
            Set<Role> rolesToAdd = new HashSet<Role>(getAllRoles());
            rolesToAdd.removeAll(employee.getRoles());
            employee.setRolesToAdd(rolesToAdd);
        }
    }

    public List<Role> getAllRoles() {
        return service.getAllRoles();
    }

    public List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    @Override
    public Employee get(long id) {
        super.get(id);
        Employee employee = service.get(id);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        super.update(employee);
        service.update(employee);
    }

    @Override
    public void delete(long id) {
        super.delete(id);
        service.delete(id);
    }

    private void addRole(long empId, long roleId) {
        LOGGER.info(String.format("Add role id{%d}, empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.get(empId);
        if (role != null && employee != null) {
            employee.addRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Add role failed. Something is null: " +
              "{%s}, {%s}", role, employee));
        }
    }

    private void deleteRole(long empId, long roleId) {
        LOGGER.info(String.format("Delete role id{%d}, empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.get(empId);
        if (role != null && employee != null) {
            employee.deleteRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Delete role failed. Something is null: " +
              "{%s}, {%s}", role, employee));
        }
    }

}
