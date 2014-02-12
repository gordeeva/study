package com.sam.app.spring.controller;

import com.sam.app.domain.Department;
import com.sam.app.domain.Employee;
import com.sam.app.domain.Role;
import com.sam.app.util.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/EmployeeServlet")
public class EmployeeController extends AbstractCRUDController<Employee>{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private static final String EMPLOYEE_VIEW = "employees";

    private static final String ADD_ROLE_ACTION_NAME = "addRole";

    private static final String DELETE_ROLE_ACTION_NAME = "deleteRole";

    private static final String UPDATE_EMPLOYEE_ACTION_NAME = "update";

    private static final String ADD_EMPLOYEE_ACTION_NAME = "add";

    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";

    private static final String EMPLOYEES_ATTRIBUTE_NAME = "employees";

    private static final String ROLES_ATTRIBUTE_NAME = "roles";

    @Autowired
    private EmployeeService service;

    private ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();


    @RequestMapping(method = RequestMethod.GET,
      params = {"action", "lang"})
    public String updateLocale(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "lang") String lang,
      Model model) {
        modelThreadLocal.set(model);
        if (LOCALE.equals(action)) {
            updateLocale(lang);
        }
        setAttributesForGetAll(model);

        return EMPLOYEE_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        setAttributesForGetAll(model);

        return EMPLOYEE_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET,
      params = {"action", "id"})
    public String getOther(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id") Long id,
      Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        if (READ_ACTION.equals(action)) {
            if (validateID(id)) {
                setAttributesForGetFew(Collections.singletonList(get(id)), model);
            } else {
                setErrorAttribute("ERROR_ID", model);
                setAttributesForGetAll(model);
            }
        } else if (DELETE_ACTION.equals(action)) {
            if (validateID(id)) {
                delete(id);
            } else {
                setErrorAttribute("ERROR_ID", model);
            }
            setAttributesForGetAll(model);
        }

        return EMPLOYEE_VIEW;
    }

    private boolean validateID(Long idParam) {
        return service.getEmployee(idParam) != null;
    }

    private void setAttributesForGetAll(Model model) {
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAllDepartments());
        model.addAttribute(EMPLOYEES_ATTRIBUTE_NAME, getAll());
        model.addAttribute(ROLES_ATTRIBUTE_NAME, getAllRoles());
    }

    private void setAttributesForGetFew(List<Employee> employees, Model model) {
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAllDepartments());
        model.addAttribute(EMPLOYEES_ATTRIBUTE_NAME, employees);
        model.addAttribute(ROLES_ATTRIBUTE_NAME, getAllRoles());
    }

    private List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    private List<Role> getAllRoles() {
        return service.getAllRoles();
    }

    @RequestMapping(method = RequestMethod.POST,
      params = {"action", "id", "new_roles"})
    public String addRole(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "new_roles") Long roleId,
      Model model) {
        updateLocale();
        if (ADD_ROLE_ACTION_NAME.equals(action)) {
            addRole(id, roleId);
        }
        setAttributesForGetAll(model);
        return EMPLOYEE_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST,
      params = {"action", "id", "existing_roles"})
    public String deleteRole(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "existing_roles") Long roleId,
      Model model) {
        updateLocale();
        if (DELETE_ROLE_ACTION_NAME.equals(action)) {
            deleteRole(id, roleId);
        }
        setAttributesForGetAll(model);
        return EMPLOYEE_VIEW;
    }


        @RequestMapping(method = RequestMethod.POST,
      params = {"action", "id", "name"})
    public String employeesPost(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "department") Long departmentId,
      Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        Employee employee = new Employee();
        employee.setName(name);
        employee.setDepartment(service.getDepartment(departmentId));
        if (UPDATE_EMPLOYEE_ACTION_NAME.equals(action)) {
            employee.setId(id);
            employee.setRoles(service.getEmployee(id).getRoles());
            update(employee);
        } else if (ADD_EMPLOYEE_ACTION_NAME.equals(action)) {
            create(employee);
        }
        setAttributesForGetAll(model);

        return EMPLOYEE_VIEW;
    }

    private void addRole(long empId, long roleId) {
        LOGGER.info(String.format("Add role id{%d} to employee empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.getEmployee(empId);
        if (role != null && employee != null) {
            employee.addRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Add role to employee failed. Something is null: " +
              "{%s}, {%s}", role, employee));
            setErrorAttribute("ERROR_EMPLOYEE_ROLE_ADD", modelThreadLocal.get());
        }
    }

    private void deleteRole(long empId, long roleId) {
        LOGGER.info(String.format("Delete role id{%d} from employee empId{%d} was called", roleId, empId));
        Role role = service.getRole(roleId);
        Employee employee = service.getEmployee(empId);
        if (role != null && employee != null) {
            employee.deleteRole(role);
            service.update(employee);
        } else {
            LOGGER.warn(String.format("Delete role from employee failed. Something is null: " +
              "{%s}, {%s}", role, employee));
            setErrorAttribute("ERROR_EMPLOYEE_ROLE_DELETE", modelThreadLocal.get());
        }
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

    private boolean validateEmployee(Employee employee) {
        boolean result = true;
        if (StringUtils.isEmpty(employee.getName())) {
            setErrorAttribute("ERROR_EMPLOYEE_NAME_EMPTY", modelThreadLocal.get());
            result = false;
        } else if (employee.getDepartment() == null || service.getDepartment(employee.getDepartment().getId()) == null) {
            setErrorAttribute("ERROR_EMPLOYEE_DEPARTMENT", modelThreadLocal.get());
            result = false;
        } else if (employee.getId() != null) {
            Employee oldEmployee = service.getEmployee(employee.getId());
            if (oldEmployee.getName().equals(employee.getName()) &&
                oldEmployee.getDepartment().equals(employee.getDepartment())) {
                setErrorAttribute("ERROR_EMPLOYEE_NAME_DUPLICATE", modelThreadLocal.get());
                result = false;
            }
        }
        return result;
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

    @Override
    public Employee get(long id) {
        super.get(id);
        return service.getEmployee(id);
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

    @Override
    public void delete(long id) {
        super.delete(id);
        service.delete(id);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
