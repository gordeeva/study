package com.sam.app.servlet;

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
import java.util.Collections;
import java.util.List;

@WebServlet("/RoleServlet")
public class RoleServlet extends AbstractCRUDServlet<Role> {

    private static final Logger LOGGER = LoggerFactory
      .getLogger(RoleServlet.class);

    private static final long serialVersionUID = 1454943414322885268L;

    private static final String UPDATE_ROLE_ACTION_NAME = "update";

    private static final String ADD_ROLE_ACTION_NAME = "add";

    private static final String ROLES_ATTRIBUTE_NAME = "roles";

    private static final String ROLE_VIEW_NAME = "/role.jsp";

    private AbstractEntityService<Role> service = new AbstractEntityService<Role>(
      Role.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action") == null ? "" : req
          .getParameter("action");
        if (action.equals(READ_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            setAttributesForGetFew(Collections.singletonList(get(id)), req);
        } else if (action.equals(DELETE_ACTION)) {
            long id = Long.valueOf(req.getParameter("id"));
            delete(id);
            setAttributesForGetAll(req);
        } else if (action.equals(LOCALE)) {
            updateLocale(req);
            setAttributesForGetAll(req);
        } else {
            setAttributesForGetAll(req);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher(ROLE_VIEW_NAME);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doGet() failed", e);
        }
    }

    private void setAttributesForGetAll(HttpServletRequest req) {
        req.setAttribute(ROLES_ATTRIBUTE_NAME, getAll());
    }

    private void setAttributesForGetFew(List<Role> roles, HttpServletRequest req) {
        req.setAttribute(ROLES_ATTRIBUTE_NAME, roles);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        action = action == null ? "" : action;
        Role role = new Role();
        role.setName(req.getParameter("name"));
        if (UPDATE_ROLE_ACTION_NAME.equals(action)) {
            long id = Long.valueOf(req.getParameter("id"));
            role.setId(id);
            update(role);
        } else if (ADD_ROLE_ACTION_NAME.equals(action)) {
            create(role);
        }
        setAttributesForGetAll(req);
        RequestDispatcher dispatcher = req.getRequestDispatcher(ROLE_VIEW_NAME);
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doPost() failed", e);
        }
    }

    @Override
    public long create(Role role) {
        super.create(role);
        return service.create(role);
    }

    @Override
    public List<Role> getAll() {
        super.getAll();
        return service.getAll();
    }

    @Override
    public Role get(long id) {
        super.get(id);
        return service.get(id);
    }

    @Override
    public void update(Role role) {
        super.update(role);
        service.update(role);
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
