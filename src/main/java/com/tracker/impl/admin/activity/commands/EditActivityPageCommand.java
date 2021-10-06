package com.tracker.impl.admin.activity.commands;

import com.tracker.Command;
import com.tracker.impl.admin.activity.*;
import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiresRole(UserRolesEnum.ADMIN)
public class EditActivityPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditActivityPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = editActivityProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Data search done");
        } else {
            request.setAttribute("actionStatus", "Data search failed");
            log.log(Level.DEBUG, "Activities main page data processing error");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean editActivityProcess(HttpServletRequest processRequest) {

        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        Integer editActID;
        Integer editCatID;
        try {
            editActID = Integer.parseInt(processRequest.getParameter("activityId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Activity ID parse error");
            log.log(Level.DEBUG, "Activity ID parse error", n);
            return false;
        }
        Activity editAct = activityService.getActivity(editActID);
        List<Category> categories = categoryService.searchCategories("");

        try {
            editCatID = Integer.parseInt(processRequest.getParameter("categoryId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Category ID parse error");
            log.log(Level.DEBUG, "Category ID parse error", n);
            return false;
        }

        Category editCat = categoryService.getCategoryByID(editCatID);
        processRequest.getSession().setAttribute("editCategory", editCat);
        processRequest.getSession().setAttribute("editActivity", editAct);
        processRequest.getSession().setAttribute("searchCategories", categories);

        if (categories.isEmpty() || editAct.getActivityName().isEmpty()) {
            processRequest.setAttribute("error", "No data");
            log.log(Level.ERROR, "No data");
            return false;
        }
        return true;
    }
}
