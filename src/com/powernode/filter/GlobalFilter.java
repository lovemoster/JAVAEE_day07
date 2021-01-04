package com.powernode.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Object user = req.getSession().getAttribute("username");
        if (user == null || "".equals(user)) {
            if (req.getRequestURI().contains("login.jsp")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (req.getRequestURI().contains("main.do") && "login".equals(req.getParameter("action"))) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
