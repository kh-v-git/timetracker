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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiresRole(UserRolesEnum.ADMIN)
public class GetActivitiesMainPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetActivitiesMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (mainActivityProcess(request)) {
            request.setAttribute("actionStatus", "Data search done");
        } else {
            request.setAttribute("actionStatus", "Data search failed");
            log.log(Level.DEBUG, "Activities main page data processing error");
        }

        request.getRequestDispatcher("pages/admin/activity/activity_main.jsp").forward(request, response);
    }

    private boolean mainActivityProcess(HttpServletRequest processRequest) {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);
        Integer categoryId = -1;
        List<Category> categories = new ArrayList<>();
        List<Activity> activities = new ArrayList<>();
        try {
            categoryId = Optional.ofNullable(processRequest.getParameter("searchCategoryId"))
                    .filter(searchId -> !searchId.isEmpty())
                    .map(Integer::parseInt)
                    .orElse(-1);
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Category ID parse error");
            processRequest.getSession().setAttribute("searchCategories", categories);
            processRequest.getSession().setAttribute("searchActivities", activities);
            processRequest.getSession().setAttribute("searchCategoryId", categoryId);
            log.log(Level.ERROR, "Category ID parse error", n);
            return false;
        }
        String searchCat = Optional.ofNullable(processRequest.getParameter("search-category"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String searchAct = Optional.ofNullable(processRequest.getParameter("searchText"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

       categories = categoryService.searchCategories(searchCat);
       activities = activityService.searchActivityList(searchAct, categoryId);

        processRequest.getSession().setAttribute("searchCategories", categories);
        processRequest.getSession().setAttribute("searchActivities", activities);
        processRequest.getSession().setAttribute("searchCategoryId", categoryId);
        processRequest.setAttribute("searchText", searchAct);
        if (activities.isEmpty() || categories.isEmpty()) {
            processRequest.setAttribute("error", "No data found");
            log.log(Level.DEBUG, "Empty activity list");
            return false;
        }
        return true;
    }
}
