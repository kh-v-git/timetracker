package com.tracker.admin.activity.commands;

import com.tracker.Command;
import com.tracker.admin.category.Category;
import com.tracker.admin.category.CategoryRepository;
import com.tracker.admin.category.CategoryRepositorySQLImpl;
import com.tracker.admin.category.CategoryService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetNewActivityPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetNewActivityPageCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository =  new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);


        List<Category> categories = categoryService.searchCategories("");

        if (categories.isEmpty()) {
            request.setAttribute("error", "Wrong category request");
            log.log(Level.ERROR, "Wrong category request");
        }

        request.getSession().setAttribute("searchCategories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/activity/activity_add_new.jsp");
        requestDispatcher.forward(request, response);
    }
}
