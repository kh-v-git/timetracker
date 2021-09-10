package com.helloworld.security.commands;

import com.helloworld.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticateCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("user-email");
        String password = request.getParameter("user-password");

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
