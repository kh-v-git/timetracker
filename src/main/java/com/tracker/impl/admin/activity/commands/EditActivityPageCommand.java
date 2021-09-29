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
        CategoryRepository categoryRepository =  new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);


        int editActID = Integer.parseInt(request.getParameter("activityId"));
        Activity editAct = activityService.getActivity(editActID);

        List<Category> categories = categoryService.searchCategories("");
        int editCatID = Integer.parseInt(request.getParameter("categoryId"));
        Category editCat = categoryService.getCategoryByID(editCatID);

        request.getSession().setAttribute("editCategory", editCat);
        if (!categories.isEmpty()) {
            request.getSession().setAttribute("searchCategories", categories);
        } else {
            request.setAttribute("error", "Wrong category request");
            log.log(Level.ERROR, "Wrong category request");
        }
        if (!editAct.getActivityName().isEmpty()) {
            request.getSession().setAttribute("editActivity", editAct);
        } else {
            request.setAttribute("error", "Wrong activity request");
            log.log(Level.ERROR, "Wrong category request");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
        requestDispatcher.forward(request, response);
    }
}
