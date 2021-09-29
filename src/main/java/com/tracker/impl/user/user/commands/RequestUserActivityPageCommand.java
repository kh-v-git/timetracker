package com.tracker.impl.user.user.commands;

import com.google.gson.Gson;
import com.tracker.Command;
import com.tracker.impl.admin.activity.ActivityRepository;
import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.impl.common.data.CategoryActivitiesList;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@RequiresRole(UserRolesEnum.USER)
public class RequestUserActivityPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(RequestUserActivityPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pageUserActivityProcess(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/user/user_request_activity.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean pageUserActivityProcess(HttpServletRequest processRequest) {

        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);
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
        if (categoryList.isEmpty()) {
            processRequest.setAttribute("error", "No accepted activities");
            return false;
        }

        String categoryActivityList = new Gson().toJson(categoryActivitiesLists);
        processRequest.getSession().setAttribute("categoryActivities", categoryActivityList);
        return true;
    }
}
