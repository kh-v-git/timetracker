package com.tracker.admin.category.commands;

import com.tracker.Command;
import com.tracker.admin.category.CategoryRepository;
import com.tracker.admin.category.CategoryRepositoryInMemoryImpl;
import com.tracker.admin.category.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AddNewCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(AddNewCategoryCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);

        String addCatName = Optional.ofNullable(request.getParameter("catNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String addCatDescription = Optional.ofNullable(request.getParameter("catNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        boolean createCatDone = categoryService.setNewCategoryByName(addCatName, addCatDescription);

        if (!createCatDone) {
            request.setAttribute("error", "Category add error");
            log.log(Level.ERROR, "Category add error");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/get_categories_list.command");
        requestDispatcher.forward(request, response);

    }
}
