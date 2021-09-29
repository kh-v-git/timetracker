package com.tracker.impl.user.user.commands;

import com.google.gson.Gson;
import com.tracker.Command;
import com.tracker.impl.admin.activity.Activity;
import com.tracker.impl.admin.activity.ActivityRepository;
import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.impl.common.data.CategoryActivitiesList;
import com.tracker.impl.user.useractivity.UserActivity;
import com.tracker.impl.user.useractivity.UserActivityRepository;
import com.tracker.impl.user.useractivity.UserActivitySQLImpl;
import com.tracker.impl.user.useractivity.UserActivityService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RequiresRole(UserRolesEnum.USER)
public class LogNewActivityUserPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogNewActivityUserPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!pageUserActivityProcess(request)) {
            request.setAttribute("actionStatus", "No data found");
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/user/user_log_activity.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean pageUserActivityProcess(HttpServletRequest processRequest) {
        int userId;
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);
        UserActivityRepository userActivityRepository = new UserActivitySQLImpl();
        UserActivityService userActivityService = new UserActivityService(userActivityRepository);
        List<CategoryActivitiesList> categoryActivitiesLists = new ArrayList<>();
        CategoryActivitiesList categoryActivities;

        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand", n);
            return false;
        }

        List<UserActivity> userALLActivityList = userActivityService.getUserActivityList();
        List<Integer> userActivityIdALLList = userALLActivityList.stream()
                .filter(activity -> activity.getUserId() == userId && "accepted".equals(activity.getActivityStatus()))
                .map(UserActivity::getActivityId)
                .distinct()
                .collect(Collectors.toList());
        if (userActivityIdALLList.isEmpty()) {
            processRequest.setAttribute("error", "No user activities");
            return false;
        }

        List<Activity> activityALLUserList = new ArrayList<>();
        for (int actId : userActivityIdALLList) {
            Activity newActivity = activityService.getActivity(actId);
            activityALLUserList.add(newActivity);
        }
        List<Integer> categoryIdUserList = activityALLUserList.stream()
                .map(Activity::getActivityCatId)
                .distinct()
                .collect(Collectors.toList());

        for (int catId : categoryIdUserList) {
            categoryActivities = new CategoryActivitiesList();
            categoryActivities.setCategoryId(catId);
            categoryActivities.setCategoryName(categoryService.getCategoryByID(catId).getCategoryName());
            List<Activity> currentCategoryActivityList = activityALLUserList.stream()
                    .filter(activity -> activity.getActivityCatId() == catId)
                    .collect(Collectors.toList());
            categoryActivities.setCategoryActivitiesList(currentCategoryActivityList);

            categoryActivitiesLists.add(categoryActivities);
        }
        String categoryActivityList = new Gson().toJson(categoryActivitiesLists);
        processRequest.getSession().setAttribute("categoryActivities", categoryActivityList);
        return true;
    }
}
