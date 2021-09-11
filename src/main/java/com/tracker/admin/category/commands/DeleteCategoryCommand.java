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


public class DeleteCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteCategoryCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository = CategoryRepositoryInMemoryImpl.getInstance();
        CategoryService categoryService = new CategoryService(categoryRepository);

        int editCatID;
        boolean deleteStatus = false;

        try{
         editCatID = Integer.parseInt(request.getParameter("category_id"));
         deleteStatus = categoryService.deleteCategoryByID(editCatID);
        }catch (NumberFormatException n) {
            log.log(Level.ERROR, "Category ID parse error", n);
        }

        if (!deleteStatus) {
            request.setAttribute("error", "Delete process failed. No Id found.");
            log.log(Level.ERROR, "Category delete process failed. No Id found.");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/get_categories_list.command");
        requestDispatcher.forward(request, response);
    }
}
