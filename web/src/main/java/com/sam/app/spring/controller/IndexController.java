package com.sam.app.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = { "/", "/IndexServlet" })
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final String INDEX_VIEW = "index";


    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model) {
        AbstractCRUDController.updateLocale();
        return INDEX_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET,
      params = { "action", "lang" })
    public String updateLocale(
      @RequestParam(value = "action") String action,
      @RequestParam(value = "lang") String lang,
      Model model) {
        if (AbstractCRUDController.LOCALE.equals(action)) {
            AbstractCRUDController.updateLocale(lang);
        }
        return INDEX_VIEW;
    }
}
