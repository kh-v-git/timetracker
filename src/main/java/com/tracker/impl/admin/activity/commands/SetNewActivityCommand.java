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
        boolean executeStatus = false;
        try {
            executeStatus = addActivityProcess(request);
            request.setAttribute("actionStatus", "Set new activity success.");
        } catch (NumberFormatException n) {
            request.setAttribute("error", "Category ID parse error");
            log.log(Level.ERROR, "Category ID parse error", n);
        }
        if (!executeStatus) {
            request.setAttribute("actionStatus", "Activity add failed");
            log.log(Level.ERROR, "Category ID parse error");
        }

        request.getRequestDispatcher("get_activities_main.command").forward(request, response);
    }

    private boolean addActivityProcess(HttpServletRequest processRequest) throws NumberFormatException {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);

        int categoryID = Integer.parseInt(processRequest.getParameter("categoryId"));
        String addActName = Optional.ofNullable(processRequest.getParameter("actNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String addActDescription = Optional.ofNullable(processRequest.getParameter("actNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");


        if (addActName.isEmpty()|| addActDescription.isEmpty()) {
            return false;
        } else {
            Activity newActivity = new Activity();
            newActivity.setActivityName(addActName);
            newActivity.setActivityCatId(categoryID);
            newActivity.setActivityDescription(addActDescription);
            return  activityService.setActivity(newActivity);
        }
    }
}
