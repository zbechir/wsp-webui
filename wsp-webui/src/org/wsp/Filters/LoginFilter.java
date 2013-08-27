package org.wsp.Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {

	private FilterConfig config;
	private static final Logger log = Logger.getLogger(LoginFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) arg0).getSession();
		HttpServletResponse response = (HttpServletResponse) arg1;
		config.getServletContext();
		if (session.getAttribute("user") == null) {
			response.sendRedirect("../index.jsf");
			log.info("Redirecting to index.jsf");
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("Instance created of " + getClass().getName());
		this.config = arg0;
	}

}
