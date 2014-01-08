package com.sam.app.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletFilter implements Filter {

	private static final String FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded";

	private static final String ENCODING_DEFAULT = "UTF-8";

	private static final String ENCODING_INIT_PARAM_NAME = "encoding";

	private String encoding;

	@Override
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter(ENCODING_INIT_PARAM_NAME);
		if (encoding == null)
			encoding = ENCODING_DEFAULT;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// System.out.println("before filter: " + request.getParameter("name"));
//        String contentType = request.getContentType();
//        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE)) {
        	request.setCharacterEncoding(encoding);
//        }
//		System.out.println("after filter: " + request.getParameter("name"));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// do nothing
	}

}
