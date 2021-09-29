package com.tracker.security.commands;

import com.tracker.Command;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiresRole(UserRolesEnum.ADMIN)
public class SecuredUserCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/admin_secured.jsp");
        requestDispatcher.forward(request, response);
    }
}
