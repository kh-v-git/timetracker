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

public class UpdateCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateCategoryCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);

        int editCatID;
        boolean updateStatus = false;

        try{
            editCatID = Integer.parseInt(request.getParameter("category_id"));
            String updateCatName = Optional.ofNullable(request.getParameter("catNewName"))
                    .map(Object::toString)
                    .map(String::trim)
                    .orElse("");
            String updateCatDescription = Optional.ofNullable(request.getParameter("catNewDescription"))
                    .map(Object::toString)
                    .map(String::trim)
                    .orElse("");
            updateStatus = categoryService.updateCategoryByID(editCatID, updateCatName, updateCatDescription);
        }catch (NumberFormatException n) {
            log.log(Level.ERROR, "Category ID parse error", n);
        }

        if (!updateStatus) {
            request.setAttribute("error", "Update process failed. No Id found.");
            log.log(Level.ERROR, "Category update process failed. No Id found.");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/get_categories_list.command");
        requestDispatcher.forward(request, response);
    }
}
