package com.tracker.security.commands;

import com.tracker.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        session.setAttribute("email", null);
        session.setAttribute("role", null);


        response.sendRedirect("login.command");
    }
}
