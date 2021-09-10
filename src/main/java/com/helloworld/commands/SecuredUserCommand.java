package com.helloworld.commands;

import com.helloworld.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecuredUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/admin_secured.jsp");
        requestDispatcher.forward(request, response);
    }
}
