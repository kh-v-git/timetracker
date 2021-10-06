package com.tracker.impl.admin.activity.commands;

import com.tracker.Command;
import com.tracker.impl.admin.activity.Activity;
import com.tracker.impl.admin.activity.ActivityRepository;
import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiresRole(UserRolesEnum.ADMIN)
public class SetNewActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(SetNewActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = addActivityProcess(request);
        if (executeStatus) {
            request.setAttribute("actionStatus", "Activity add success");
        } else {
            request.setAttribute("actionStatus", "Activity add failed");
        }
        request.getRequestDispatcher("get_activities_main.command").forward(request, response);
    }

    private boolean addActivityProcess(HttpServletRequest processRequest) {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        Integer categoryID;
        try {
            categoryID = Integer.parseInt(processRequest.getParameter("categoryId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Category ID parse error");
            log.log(Level.ERROR, "Category ID parse error", n);
            return false;
        }
        String addActName = Optional.ofNullable(processRequest.getParameter("actNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String addActDescription = Optional.ofNullable(processRequest.getParameter("actNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        if (addActName.isEmpty() || addActDescription.isEmpty()) {
            log.log(Level.DEBUG, "Activity data empty");
            return false;
        }
        Activity newActivity = new Activity();
        newActivity.setActivityName(addActName);
        newActivity.setActivityCatId(categoryID);
        newActivity.setActivityDescription(addActDescription);
        return activityService.setActivity(newActivity);

    }
}
