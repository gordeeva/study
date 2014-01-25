package com.sam.app.servlet;

import org.slf4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public abstract class AbstractCRUDServlet<T> extends HttpServlet {

    private static final long serialVersionUID = 3542484734046589300L;

    protected static final String READ_ACTION = "get";

    protected static final String DELETE_ACTION = "delete";

    public static final String LOCALE = "locale";


    public long create(T t) {
        getLogger().info("Create of " + t + " was called");
        return -1;
    }

    public List<T> getAll() {
        getLogger().info("GetAll() was called");
        return Collections.emptyList();
    }

    public T get(long id) {
        getLogger().info(String.format("Get(%d)  was called", id));
        return null;
    }

    public void update(T t) {
        getLogger().info(String.format("Update(%s) was called", t));
    }

    public void delete(long id) {
        getLogger().info(String.format("Delete(%d) was called", id));
    }

    protected void updateLocale(HttpServletRequest req) {
        String lang = req.getParameter("lang");
        getLogger().info(String.format("Update locale(%s) was called", lang));
        Locale locale;
        if ("en".equals(lang)) {
            locale = new Locale("en", "EN");
        } else if ("ru".equals(lang)) {
            locale = new Locale("ru", "RU");
        } else {
            locale = req.getLocale();
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("lang", locale);
    }

    abstract protected Logger getLogger();
}
