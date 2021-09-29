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
import java.util.List;
import java.util.Optional;
@RequiresRole(UserRolesEnum.ADMIN)
public class GetActivitiesMainPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetActivitiesMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = false;
        try {
            executeStatus = mainActivityProcess(request);

        } catch (NumberFormatException n) {
            request.setAttribute("error", "Category ID parse error");
            executeStatus = false;
            log.log(Level.ERROR, "Category ID parse error", n);
        }
        if (!executeStatus) {
            request.setAttribute("actionStatus", "Data search failed.");
            log.log(Level.ERROR, "Category ID parse error");
        }
        request.getRequestDispatcher("pages/admin/activity/activity_main.jsp").forward(request, response);
    }

    private boolean mainActivityProcess(HttpServletRequest processRequest) throws NumberFormatException {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        Integer categoryId = Optional.ofNullable(processRequest.getParameter("searchCategoryId"))
                .filter(searchId -> !searchId.isEmpty())
                .map(Integer::parseInt)
                .orElse(-1);
        String searchCat = Optional.ofNullable(processRequest.getParameter("search-category"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String searchAct = Optional.ofNullable(processRequest.getParameter("searchText"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        List<Category> categories = categoryService.searchCategories(searchCat);
        List<Activity> activities = activityService.searchActivityList(searchAct, categoryId);

        processRequest.getSession().setAttribute("searchCategories", categories);
        processRequest.getSession().setAttribute("searchActivities", activities);
        processRequest.getSession().setAttribute("searchCategoryId", categoryId);
        processRequest.setAttribute("searchText", searchAct);
        if (activities.isEmpty()) {
            processRequest.setAttribute("error", "No activities found");
            log.log(Level.DEBUG, "Empty activity list");
        }
        return true;
    }
}
