package com.sam.app.servlet;

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

    private ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action") == null ? "" : req
          .getParameter("action");
        if (action.equals(READ_ACTION)) {
            String idParam = req.getParameter("id");
            if (validateID(idParam)) {
                long id = Long.valueOf(idParam);
                setAttributesForGetFew(Collections.singletonList(get(id)), req);
            } else {
                setErrorAttribute("ERROR_ID", req);
                setAttributesForGetAll(req);
            }
        } else if (action.equals(DELETE_ACTION)) {
            String idParam = req.getParameter("id");
            if (validateID(idParam)) {
                long id = Long.valueOf(idParam);
                delete(id);
            } else {
                setErrorAttribute("ERROR_ID", req);
            }
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

    private boolean validateID(String idParam) {
        return !Utils.isEmpty(idParam) &&
          service.get(Long.valueOf(idParam)) != null;
    }

    private void setAttributesForGetAll(HttpServletRequest req) {
        req.setAttribute(ROLES_ATTRIBUTE_NAME, getAll());
    }

    private void setAttributesForGetFew(List<Role> roles, HttpServletRequest req) {
        req.setAttribute(ROLES_ATTRIBUTE_NAME, roles);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestThreadLocal.set(req);
        super.service(req, resp);
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
        if (validateRole(role)) {
            super.create(role);
            return service.create(role);
        } else {
            LOGGER.warn(String.format("Create role failed. %nReason: " +
              "Role validation failed %s", role));
            return -1;
        }
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
        if (validateRole(role)) {
            super.update(role);
            service.update(role);
        } else {
            LOGGER.warn(String.format("Update role failed. %nReason: " +
              "Role validation failed %s", role));
        }
    }

    private boolean validateRole(Role role) {
        boolean result = true;
        if (Utils.isEmpty(role.getName()) || !service.getRolesByName(role.getName()).isEmpty()) {
            setErrorAttribute("ERROR_ROLE_NAME_DUPLICATE", requestThreadLocal.get());
            result = false;
        }
        return result;
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
