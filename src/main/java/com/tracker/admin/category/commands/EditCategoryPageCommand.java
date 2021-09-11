package com.tracker.admin.category.commands;

import com.tracker.Command;
import com.tracker.admin.category.Category;
import com.tracker.admin.category.CategoryRepository;
import com.tracker.admin.category.CategoryRepositoryInMemoryImpl;
import com.tracker.admin.category.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditCategoryPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditCategoryPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);


        int editCatID = Integer.valueOf(request.getParameter("category_id"));
        Category editCat = categoryService.getCategoryByID(editCatID);
        request.getSession().setAttribute("edit_category", editCat);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/category_edit.jsp");
        requestDispatcher.forward(request, response);
    }
}
