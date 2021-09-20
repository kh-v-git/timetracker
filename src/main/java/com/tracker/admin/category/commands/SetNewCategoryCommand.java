package com.tracker.admin.category.commands;

import com.tracker.Command;
import com.tracker.admin.category.CategoryRepository;
import com.tracker.admin.category.CategoryRepositorySQLImpl;
import com.tracker.admin.category.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SetNewCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(SetNewCategoryCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        String addCatName = Optional.ofNullable(request.getParameter("catNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String addCatDescription = Optional.ofNullable(request.getParameter("catNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        if (addCatName.isEmpty() || addCatDescription.isEmpty()) {
            request.setAttribute("error", "Category add error");
            log.log(Level.ERROR, "Category add error");
        } else {
            categoryService.createNewCategory(addCatName, addCatDescription);
            request.setAttribute("actionStatus", "Set new category success.");
        }

        request.getRequestDispatcher("get_categories_main.command").forward(request, response);
    }
}
