package com.sam.app.servlet;

import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import com.sam.app.util.AbstractEntityService;
import com.sam.app.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = { "/EmployeeServlet" })
public class EmployeeServlet extends AbstractCRUDServlet<Employee> {

    private static final long serialVersionUID = 3918551416797741287L;

    private static Logger LOGGER = LoggerFactory
      .getLogger(EmployeeServlet.class);

    private static final String ADD_ROLE_ACTION_NAME = "addRole";

    private static final String DELETE_ROLE_ACTION_NAME = "deleteRole";

    private static final String UPDATE_EMPLOYEE_ACTION_NAME = "update";

    private static final String ADD_EMPLOYEE_ACTION_NAME = "add";

    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";

    private static final String EMPLOYEES_ATTRIBUTE_NAME = "employees";

    private static final String ROLES_ATTRIBUTE_NAME = "roles";

    private static final String EMPLOYEE_VIEW_NAME = "/employee.jsp";

    private AbstractEntityService<Employee> service = new AbstractEntityService<Employee>(
      Employee.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if (READ_ACTION.equals(action)) {
            String idParam = req.getParameter("id");
            if (validateID(idParam)) {
                long id = Long.valueOf(idParam);
                setAttributesForGetFew(Collections.singletonList(get(id)), req);
            } else {
                setErrorAttribute("ERROR_ID", req);
                setAttributesForGetAll(req);
            }
        } else if (DELETE_ACTION.equals(action)) {
            String idParam = req.getParameter("id");
            if (validateID(idParam)) {
                long id = Long.valueOf(idParam);
                delete(id);
            } else {
                setErrorAttribute("ERROR_ID", req);
            }
            setAttributesForGetAll(req);
        } else if (LOCALE.equals(action)) {
            updateLocale(req);
            setAttributesForGetAll(req);
        } else {
            setAttributesForGetAll(req);
        }
        RequestDispatcher requestDispatcher = req
          .getRequestDispatcher(EMPLOYEE_VIEW_NAME);
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doGet() failed", e);
        }
    }

    private boolean validateID(String idParam) {
        return !Utils.isEmpty(idParam) &&
          service.get(Long.valueOf(idParam)) != null;
    }

    private void setAttributesForGetAll(HttpServletRequest req) {
        req.setAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAllDepartments());
        req.setAttribute(EMPLOYEES_ATTRIBUTE_NAME, getAll());
        req.setAttribute(ROLES_ATTRIBUTE_NAME, getAllRoles());
    }

    private void setAttributesForGetFew(List<Employee> employees, HttpServletRequest req) {
        req.setAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAllDepartments());
        req.setAttribute(EMPLOYEES_ATTRIBUTE_NAME, employees);
        req.setAttribute(ROLES_ATTRIBUTE_NAME, getAllRoles());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if (ADD_ROLE_ACTION_NAME.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            long roleId = Long.valueOf(req.getParameter("new_roles"));
            addRole(id, roleId);
        } else if (DELETE_ROLE_ACTION_NAME.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            long roleId = Long.valueOf(req.getParameter("existing_roles"));
            deleteRole(id, roleId);
        } else { // update or add employee
            Employee emp = new Employee();
            String name = req.getParameter("name");
            emp.setName(name);
            long departmentId = Long.valueOf(req.getParameter("department"));
            emp.setDepartment(service.getDepartment(departmentId));
            if (UPDATE_EMPLOYEE_ACTION_NAME.equals(action)) {
                long id = Long.valueOf(req.getParameter("id"));
                emp.setId(id);
                emp.setRoles(service.get(id).getRoles());
                update(emp);
            } else if (ADD_EMPLOYEE_ACTION_NAME.equals(action)) {
                create(emp);
            }
        }
        setAttributesForGetAll(req);
        RequestDispatcher requestDispatcher = req
          .getRequestDispatcher(EMPLOYEE_VIEW_NAME);
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doPost() failed", e);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    public long create(Employee employee) {
        if (validateEmployee(employee)) {
            super.create(employee);
            return service.create(employee);
        } else {
            LOGGER.warn(String.format("Create employee failed. %nReason: " +
              "Employee validation failed %s", employee));
            return -1;
        }
    }

    @Override
    public List<Employee> getAll() {
        super.getAll();
        List<Employee> allEmployees = service.getAllEmployees();
        setRolesToAddToEmployee(allEmployees);
        return allEmployees;
    }

    private void setRolesToAddToEmployee(List<Employee> employees) {
        List<Role> allRoles = getAllRoles();
        for (Employee employee : employees) {
            Set<Role> rolesToAdd = new HashSet<Role>(allRoles);
            rolesToAdd.removeAll(employee.getRoles());
            employee.setRolesToAdd(rolesToAdd);
        }
    }

    private List<Role> getAllRoles() {
        return service.getAllRoles();
    }

    private List<Department> getAllDepartments() {
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
        if (validateEmployee(employee)) {
            super.update(employee);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Update employee failed. %nReason: " +
              "Employee validation failed %s", employee));
        }
    }

    private boolean validateEmployee(Employee employee) {
        return !Utils.isEmpty(employee.getName()) &&
          employee.getDepartment() != null;
    }

    @Override
    public void delete(long id) {
        super.delete(id);
        service.delete(id);
    }

    private void addRole(long empId, long roleId) {
        LOGGER.info(String.format("Add role id{%d} to employee empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.get(empId);
        if (role != null && employee != null) {
            employee.addRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Add role to employee failed. Something is null: " +
              "{%s}, {%s}", role, employee));
        }
    }

    private void deleteRole(long empId, long roleId) {
        LOGGER.info(String.format("Delete role id{%d} from employee empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.get(empId);
        if (role != null && employee != null) {
            employee.deleteRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Delete role from employee failed. Something is null: " +
              "{%s}, {%s}", role, employee));
        }
    }

}
