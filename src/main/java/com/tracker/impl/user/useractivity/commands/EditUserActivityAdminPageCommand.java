package com.tracker.impl.user.useractivity.commands;

import com.google.gson.Gson;
import com.tracker.Command;
import com.tracker.impl.admin.activity.Activity;
import com.tracker.impl.admin.activity.ActivityRepository;
import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.impl.common.data.CategoryActivitiesList;
import com.tracker.impl.user.useractivity.UserActivity;
import com.tracker.impl.user.useractivity.UserActivityRepository;
import com.tracker.impl.user.useractivity.UserActivitySQLImpl;
import com.tracker.impl.user.useractivity.UserActivityService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserActivityStatusEnum;
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
public class EditUserActivityAdminPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditUserActivityAdminPageCommand.class);
    private ActivityRepository activityRepository;
    private ActivityService activityService;
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;
    private UserActivityService userActivityService;
    private UserActivityRepository userActivityRepository;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            editUserActivityPageExecute(request);
            setCategoryActivityList(request);
        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand", n);
        }

        request.getRequestDispatcher("pages/admin/user/user_admin_activity_edit.jsp").forward(request, response);
    }

    private boolean editUserActivityPageExecute(HttpServletRequest processRequest) throws NumberFormatException {
        activityRepository = new ActivityRepositorySQLImpl();
        activityService = new ActivityService(activityRepository);
        categoryRepository = new CategoryRepositorySQLImpl();
        categoryService = new CategoryService(categoryRepository);
        userActivityRepository = new UserActivitySQLImpl();
        userActivityService = new UserActivityService(userActivityRepository);

        Integer userActivityId = Optional.ofNullable(processRequest.getParameter("userActivityId"))
                .map(Integer::parseInt)
                .orElse(-1);
        if (userActivityId < 0) {
            processRequest.setAttribute("error", "UserActivityID parse error");
            log.log(Level.ERROR, "UserActivityID parse error");
            return false;
        }

        List<String> activityStatusList = UserActivityStatusEnum.getUserActivityStatuses();
        UserActivity userActivity = userActivityService.getUserActivity(userActivityId);
        Activity currentActivity = activityService.getActivity(userActivity.getActivityId());

        processRequest.getSession().setAttribute("currentActivity", currentActivity);
        processRequest.getSession().setAttribute("activityStatusList", activityStatusList);
        processRequest.getSession().setAttribute("userActivity", userActivity);
        return true;
    }

    private void setCategoryActivityList(HttpServletRequest request) {
        activityRepository = new ActivityRepositorySQLImpl();
        activityService = new ActivityService(activityRepository);
        categoryRepository = new CategoryRepositorySQLImpl();
        categoryService = new CategoryService(categoryRepository);
        List<CategoryActivitiesList> categoryActivitiesLists = new ArrayList<>();
        CategoryActivitiesList categoryActivitiesList;

        List<Category> categoryList = categoryRepository.findCategoryList("");
        for (Category category : categoryList) {
            categoryActivitiesList = new CategoryActivitiesList();
            categoryActivitiesList.setCategoryId(category.getCategoryId());
            categoryActivitiesList.setCategoryName(category.getCategoryName());
            categoryActivitiesList.setCategoryActivitiesList(activityRepository.findActivityList("", category.getCategoryId()));
            categoryActivitiesLists.add(categoryActivitiesList);
        }
        String categoryActivities = new Gson().toJson(categoryActivitiesLists);
        request.getSession().setAttribute("categoryActivities", categoryActivities);
    }

}
