package com.tracker.impl.user.useractivity.commands;

import com.tracker.Command;
import com.tracker.impl.user.useractivity.UserActivity;
import com.tracker.impl.user.useractivity.UserActivityRepository;
import com.tracker.impl.user.useractivity.UserActivitySQLImpl;
import com.tracker.impl.user.useractivity.UserActivityService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SetNewUserActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(SetNewUserActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean executeStatus = addUserActivityProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Set new user activity success.");
        } else {
            request.setAttribute("actionStatus", "User activity add failed");
            log.log(Level.ERROR, "User activity add failed");
        }
        request.getRequestDispatcher("get_user_activity_admin_page.command").forward(request, response);
    }

    private boolean addUserActivityProcess(HttpServletRequest processRequest) {
        UserActivityRepository userActivityRepository = new UserActivitySQLImpl();
        UserActivityService userActivityService = new UserActivityService(userActivityRepository);
        UserActivity newUserActivity = new UserActivity();
        int userId;
        int activityId;

        String activityStatus = dataCheck(processRequest, "activityStatus");
        if (activityStatus.isEmpty()) {
            log.log(Level.ERROR, "User activity status is empty");
            processRequest.setAttribute("error", "User activity status is empty.");
            return false;
        }
        try {
            userId = Integer.parseInt(processRequest.getParameter("userId"));
        } catch (NumberFormatException n) {
            log.log(Level.ERROR, "User ID parse error", n);
            return false;
        }
        try {
            activityId = Integer.parseInt(processRequest.getParameter("activityId"));
        } catch (NumberFormatException n) {
            log.log(Level.ERROR, "User Activity ID parse error", n);
            return false;
        }

        newUserActivity.setUserId(userId);
        newUserActivity.setActivityId(activityId);
        newUserActivity.setActivityStatus(activityStatus);
        return userActivityService.setUserActivity(newUserActivity);
    }

    private String dataCheck(HttpServletRequest request, String inputParam) {
        return Optional.ofNullable(request.getParameter(inputParam))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }
}
