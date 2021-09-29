package com.tracker.impl.admin.category.commands;

import com.tracker.Command;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RequiresRole(UserRolesEnum.ADMIN)
public class GetCategoryAddNewPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/category/categories_add_new.jsp");
        requestDispatcher.forward(request, response);
    }
}
