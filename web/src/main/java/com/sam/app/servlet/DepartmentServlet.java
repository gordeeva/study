package com.sam.app.servlet;

import com.sam.app.domain.Department;
import com.sam.app.util.AbstractEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@WebServlet("/DepartmentServlet")
public class DepartmentServlet extends AbstractCRUDServlet<Department> {

    private static final long serialVersionUID = 5763031550113580414L;

    private static final Logger LOGGER = LoggerFactory
      .getLogger(DepartmentServlet.class);

    private static final String UPDATE_DEPARTMENT_ACTION_NAME = "update";

    private static final String ADD_DEPARTMENT_ACTION_NAME = "add";

    private static final String DEPARTMENTS_ATTRIBUTE_NAME = "departments";

    private static final String DEPARTMENT_VIEW_NAME = "/department.jsp";

    private AbstractEntityService<Department> service = new AbstractEntityService<Department>(
      Department.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if (READ_ACTION.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            setAttributesForGetFew(Collections.singletonList(get(id)), req);
        } else if (DELETE_ACTION.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            delete(id);
            setAttributesForGetAll(req);
        } else if (LOCALE.equals(action)) {
            updateLocale(req);
            setAttributesForGetAll(req);
        } else {
            setAttributesForGetAll(req);
        }
        RequestDispatcher dispatcher = req
          .getRequestDispatcher(DEPARTMENT_VIEW_NAME);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doGet() failed", e);
        }
    }

    private void setAttributesForGetAll(HttpServletRequest req) {
        req.setAttribute(DEPARTMENTS_ATTRIBUTE_NAME, getAll());
    }

    private void setAttributesForGetFew(List<Department> departments, HttpServletRequest req) {
        req.setAttribute(DEPARTMENTS_ATTRIBUTE_NAME, departments);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws UnsupportedEncodingException {
        String action = req.getParameter("action");
        Department department = new Department();
        department.setName(req.getParameter("name"));
        if (UPDATE_DEPARTMENT_ACTION_NAME.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            department.setId(id);
            update(department);
        } else if (ADD_DEPARTMENT_ACTION_NAME.equals(action)) {
            create(department);
        }
        setAttributesForGetAll(req);
        RequestDispatcher dispatcher = req
          .getRequestDispatcher(DEPARTMENT_VIEW_NAME);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doPost() failed", e);
        }
    }

    @Override
    public long create(Department department) {
        super.create(department);
        return service.create(department);
    }

    @Override
    public List<Department> getAll() {
        super.getAll();
        return service.getAllDepartments();
    }

    @Override
    public Department get(long id) {
        super.get(id);
        return service.get(id);
    }

    @Override
    public void update(Department department) {
        super.update(department);
        service.update(department);
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
