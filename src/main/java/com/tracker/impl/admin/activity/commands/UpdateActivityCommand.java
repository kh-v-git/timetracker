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
public class UpdateActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean actionStatus = updateActivityProcess(request);
        if (actionStatus) {
            request.setAttribute("actionStatus", "Activity update done");
        } else {
            request.setAttribute("actionStatus", "Activity update error");
            log.log(Level.DEBUG, "Activity update error");
        }
        request.getRequestDispatcher("get_activities_main.command").forward(request, response);
    }

    private boolean updateActivityProcess(HttpServletRequest processRequest) {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);
        Integer categoryId;
        Integer actId;

        try {
            categoryId = Integer.parseInt(processRequest.getParameter("categoryId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Category ID parse error");
            log.log(Level.DEBUG, "Category ID parse error", n);
            return false;
        }
        try {
            actId = Integer.parseInt(processRequest.getParameter("activityId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Activity ID parse error");
            log.log(Level.DEBUG, "Activity ID parse error", n);
            return false;
        }
        String updateActName = Optional.ofNullable(processRequest.getParameter("actNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String updateActDescription = Optional.ofNullable(processRequest.getParameter("actNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        if (updateActName.isEmpty() || updateActDescription.isEmpty()) {
            processRequest.setAttribute("error", "Data empty");
            log.log(Level.DEBUG, "Category update failed. Name or Description is empty.");
            return false;
        }
        Activity updActivity = new Activity();
        updActivity.setActivityDescription(updateActDescription);
        updActivity.setActivityName(updateActName);
        updActivity.setActivityCatId(categoryId);
        updActivity.setActivityId(actId);
        return activityService.updateActivity(updActivity);
    }
}
