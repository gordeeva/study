package com.sam.app.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (AbstractCRUDServlet.LOCALE.equals(action)) {
            String lang = req.getParameter("lang");
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

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("doGet() failed", e);
        }
    }
}
