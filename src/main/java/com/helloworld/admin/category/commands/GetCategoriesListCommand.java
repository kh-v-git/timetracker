package com.helloworld.admin.category.commands;

import com.helloworld.admin.category.Category;
import com.helloworld.admin.category.CategoryRepository;
import com.helloworld.admin.category.CategoryRepositoryInMemoryImpl;
import com.helloworld.admin.category.CategoryService;
import com.helloworld.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;

public class GetCategoriesListCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);

        String searchCat = Optional.ofNullable(request.getParameter("searchCategories"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        List<Category> categories = categoryService.getCategoriesByName(searchCat);
        request.getSession().setAttribute("searchCategories", categories);

        if (categories.isEmpty()) {
            request.setAttribute("error", "Wrong category request");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/categories.jsp");
        requestDispatcher.forward(request, response);
    }
}

