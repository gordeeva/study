package com.sam.app.spring.controller;

import com.sam.app.domain.Role;
import com.sam.app.util.RoleService;
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
@RequestMapping("/RoleServlet")
public class RoleController extends AbstractCRUDController<Role> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private static final String ROLE_VIEW = "roles";

    private static final String UPDATE_ROLE_ACTION_NAME = "update";

    private static final String ADD_ROLE_ACTION_NAME = "add";

    private static final String ROLES_ATTRIBUTE_NAME = "roles";

    private ThreadLocal<Model> modelThreadLocal = new ThreadLocal<>();

    @Autowired
    private RoleService service;

    @RequestMapping(method = RequestMethod.GET,
      params = { "action", "lang" })
    public String updateLocale(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "lang") String lang,
      Model model) {
        modelThreadLocal.set(model);
        if (LOCALE.equals(action)) {
            updateLocale(lang);
        }
        setAttributesForGetAll(model);

        return ROLE_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        setAttributesForGetAll(model);

        return ROLE_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET,
      params = { "action", "id" })
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

        return ROLE_VIEW;
    }

    private boolean validateID(Long idParam) {
        return service.getRole(idParam) != null;
    }

    private void setAttributesForGetAll(Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_NAME, getAll());
    }

    private void setAttributesForGetFew(List<Role> roles, Model model) {
        model.addAttribute(ROLES_ATTRIBUTE_NAME, roles);
    }


    @RequestMapping(method = RequestMethod.POST,
      params = { "action", "id", "name" })
    public String rolesPost(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "id", required = false) Long id,
      @RequestParam(value = "name") String name,
      Model model) {
        modelThreadLocal.set(model);
        updateLocale();
        Role role = new Role();
        role.setName(name);
        if (UPDATE_ROLE_ACTION_NAME.equals(action)) {
            role.setId(id);
            update(role);
        } else if (ADD_ROLE_ACTION_NAME.equals(action)) {
            create(role);
        }
        setAttributesForGetAll(model);

        return ROLE_VIEW;
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
        return service.getAllRoles();
    }

    @Override
    public Role get(long id) {
        super.get(id);
        return service.getRole(id);
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
        if (StringUtils.isEmpty(role.getName())) {
            setErrorAttribute("ERROR_ROLE_NAME_EMPTY", modelThreadLocal.get());
            result = false;
        } else if (!service.getRolesByName(role.getName()).isEmpty()) {
            setErrorAttribute("ERROR_ROLE_NAME_DUPLICATE", modelThreadLocal.get());
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
