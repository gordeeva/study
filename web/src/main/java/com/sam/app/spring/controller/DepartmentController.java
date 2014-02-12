package com.sam.app.spring.controller;


import com.sam.app.domain.Department;
import com.sam.app.util.DepartmentService;
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
import java.util.List;

@Controller
@RequestMapping("/DepartmentServlet")
public class DepartmentController extends AbstractCRUDController<Department> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private static final String DEPARTMENT_VIEW = "departments";

    private static final String UPDATE_DEPARTMENT_ACTION_NAME = "update";

    private static final String ADD_DEPARTMENT_ACTION_NAME = "add";

    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";

    @Autowired
    private DepartmentService service;

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

        return DEPARTMENT_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        setAttributesForGetAll(model);

        return DEPARTMENT_VIEW;
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

        return DEPARTMENT_VIEW;
    }

    private boolean validateID(Long idParam) {
        return service.getDepartment(idParam) != null;
    }

    private void setAttributesForGetAll(Model model) {
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAll());
    }

    private void setAttributesForGetFew(List<Department> departments, Model model) {
        model.addAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departments);
    }

    @RequestMapping(method = RequestMethod.POST,
      params = {"action", "id", "name"})
    public String departmentsPost(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "name") String name,
      Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        Department department = new Department();
        department.setName(name);
        if (UPDATE_DEPARTMENT_ACTION_NAME.equals(action)) {
            department.setId(id);
            update(department);
        } else if (ADD_DEPARTMENT_ACTION_NAME.equals(action)) {
            create(department);
        }
        setAttributesForGetAll(model);

        return DEPARTMENT_VIEW;
    }

    @Override
    public long create(Department department) {
        if (validateDepartment(department)) {
            super.create(department);
            return service.create(department);
        } else {
            LOGGER.warn(String.format("Create department failed. %nReason: " +
              "Department validation failed %s", department));
            return -1;
        }
    }

    private boolean validateDepartment(Department department) {
        boolean result = true;
        if (StringUtils.isEmpty(department.getName())) {
            setErrorAttribute("ERROR_DEPARTMENT_NAME_EMPTY", modelThreadLocal.get());
            result = false;
        } else if (!service.getDepartmentsByName(department.getName()).isEmpty()) {
            setErrorAttribute("ERROR_DEPARTMENT_NAME_DUPLICATE", modelThreadLocal.get());
            result = false;
        }
        return result;
    }

    @Override
    public List<Department> getAll() {
        super.getAll();
        return service.getAllDepartments();
    }

    @Override
    public Department get(long id) {
        super.get(id);
        return service.getDepartment(id);
    }

    @Override
    public void update(Department department) {
        if (validateDepartment(department)) {
            super.update(department);
            service.update(department);
        } else {
            LOGGER.warn(String.format("Update department failed. %nReason: " +
              "Department validation failed %s", department));
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
