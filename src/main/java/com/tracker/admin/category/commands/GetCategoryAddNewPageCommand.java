package com.tracker.admin.category.commands;

import com.tracker.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCategoryAddNewPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/category/categories_add_new.jsp");
        requestDispatcher.forward(request, response);
    }
}
