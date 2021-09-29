package com.tracker.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/login.command") || path.startsWith("/authenticate.command") || path.startsWith("/register_user_page.command") || path.startsWith("/css")) {
            chain.doFilter(request, response);
            return;
        }

        String email = (String) httpRequest.getSession().getAttribute("user_email");
        if (email == null || email.isEmpty()) {
            ((HttpServletResponse) response).sendRedirect("login.command");
            return;
        }

        chain.doFilter(request, response);
    }


}

