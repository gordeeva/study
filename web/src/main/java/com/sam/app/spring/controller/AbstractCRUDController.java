package com.sam.app.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class AbstractCRUDController<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCRUDController.class);

    protected static final String READ_ACTION = "get";

    protected static final String DELETE_ACTION = "delete";

    protected static final String LOCALE = "locale";

    protected static final String ERROR_ATTRIBUTE_NAME = "errors";

    private static final Locale DEFAULT_LOCALE = new Locale("en_US");

    private static final Set<Locale> supportedLocales =
      new HashSet<>(Arrays.asList(new Locale[]{new Locale("en_US"), new Locale("ru_RU")}));

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

    public static void updateLocale(String lang) {
        LOGGER.info(String.format("Update locale(%s) was called", lang));
        Locale locale;
        if ("en".equals(lang)) {
            locale = new Locale("en", "EN");
        } else if ("ru".equals(lang)) {
            locale = new Locale("ru", "RU");
        } else {
            locale = getDefaultLocale();
        }
        setLocale(locale);
    }

    private static Locale getDefaultLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        if (!supportedLocales.contains(locale)) {
            locale = DEFAULT_LOCALE;
        }
        return locale;
    }

    private static void setLocale(Locale locale) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        session.setAttribute("lang", locale);
    }

    public static void updateLocale() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        if (session.getAttribute("lang") == null) {
            setLocale(getDefaultLocale());
        }
    }

    @SuppressWarnings("unchecked")
    protected void setErrorAttribute(String errorMessage, Model model) {
        Map<String, Object> attributes = model.asMap();
        List<String> errorMessages = (List<String>) attributes.get(ERROR_ATTRIBUTE_NAME);
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
            attributes.put(ERROR_ATTRIBUTE_NAME, errorMessages);
        }
        errorMessages.add(errorMessage);
    }

    abstract protected Logger getLogger();
}
