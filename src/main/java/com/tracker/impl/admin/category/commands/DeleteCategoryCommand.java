package com.tracker.impl.admin.category.commands;

import com.tracker.Command;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteCategoryCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CategoryRepository categoryRepository =  new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        int editCatID;
        boolean deleteStatus = false;

        try{
         editCatID = Integer.parseInt(request.getParameter("categoryId"));
         deleteStatus = categoryService.deleteCategoryByID(editCatID);
         request.setAttribute("actionStatus", "Delete process success.");
        }catch (NumberFormatException n) {
            request.setAttribute("error", "Category id not found.");
            log.log(Level.ERROR, "Category ID parse error. No Id found.", n);
        }

        if (!deleteStatus) {
            request.setAttribute("error", "Category delete error");
            log.log(Level.ERROR, "Category delete process failed.");
        }
        request.getRequestDispatcher("get_categories_main.command").forward(request, response);
    }
}
