package com.sam.app.servlet;


import java.io.IOException;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        action = action == null ? "" : action;
        if (action.equals(AbstractCRUDServlet.LOCALE)) {
            String lang = req.getParameter("lang");
            lang = lang == null ? "" : lang;
            Locale locale;
            if (lang.equals("en")) {
                locale = new Locale("en", "EN");
            } else if (lang.equals("ru")) {
                locale = new Locale("ru", "RU");
            } else {
                locale = req.getLocale();
            }
            HttpSession session = req.getSession(true);
            session.setAttribute("lang", locale);
        }

        RequestDispatcher requestDispatcher = req
                .getRequestDispatcher("/index.jsp");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException e) {
            logger.error("doGet() failed", e);
        } catch (IOException e) {
        }
    }
}
