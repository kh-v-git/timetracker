package com.tracker.security.commands;

import com.tracker.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticateCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");

        if (email.equals("root@root.com")) {
            HttpSession session = request.getSession();
            session.setAttribute("user_email", email);
            session.setAttribute("role", "root");
            response.sendRedirect("secured.command");
        } else {
            if (email.equals("user@user.com")) {
                HttpSession session = request.getSession();
                session.setAttribute("user_email", email);
                session.setAttribute("role", "user");
                response.sendRedirect("index.command");
            } else {
                request.setAttribute("error", "Wrong username or password");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/login.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }
}
