package com.tracker.admin.category.commands;

import com.tracker.Command;
import com.tracker.admin.category.CategoryRepository;
import com.tracker.admin.category.CategoryRepositoryInMemoryImpl;
import com.tracker.admin.category.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCategoryPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/categories_add_new.jsp");
        requestDispatcher.forward(request, response);
    }
}
