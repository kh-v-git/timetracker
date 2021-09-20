package com.tracker.admin.category.commands;

import com.tracker.admin.category.*;
import com.tracker.Command;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class GetCategoriesMainPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetCategoriesMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository =  new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        String searchText = Optional.ofNullable(request.getParameter("searchText"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");


        List<Category> categories = categoryService.searchCategories(searchText);

        if (categories.isEmpty()) {
            request.setAttribute("error", "No categories found");
            log.log(Level.DEBUG, "Empty categories list");
        }
        request.getSession().setAttribute("searchText", searchText);
        request.getSession().setAttribute("searchCategories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/category/categories.jsp");
        requestDispatcher.forward(request, response);
    }
}

