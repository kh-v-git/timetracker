package com.tracker.security.commands;

import com.tracker.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticateCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("user-email").trim();
        String password = request.getParameter("user-password").trim();

        if (email.equals("vadym.khomenko@gmail.com")) {

            HttpSession session = request.getSession();
            session.setAttribute("user-email", email);
            session.setAttribute("role", "user");
            response.sendRedirect("secured.command");
        } else {
            request.setAttribute("error", "Wrong username or password");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/login.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
