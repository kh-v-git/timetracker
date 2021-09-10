package com.helloworld.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        if (path.startsWith("/login.command") || path.startsWith("/authenticate.command") ) {
            chain.doFilter(request, response);
            return;
        }

        String email = (String) httpRequest.getSession().getAttribute("user-email");
        if (email == null || email.isEmpty()) {
            ((HttpServletResponse)response).sendRedirect("login.command");
            return;
        }

        //TODO add logic to check user role vs request path

        chain.doFilter(request, response);

    }



}

