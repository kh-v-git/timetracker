package com.tracker.impl.user.useractivity.commands;

import com.tracker.Command;
import com.tracker.impl.user.useractivity.UserActivity;
import com.tracker.impl.user.useractivity.UserActivityRepository;
import com.tracker.impl.user.useractivity.UserActivitySQLImpl;
import com.tracker.impl.user.useractivity.UserActivityService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@RequiresRole(UserRolesEnum.ADMIN)
public class UpdateUserActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateUserActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = false;
        try {
            executeStatus = updateUserActivityExecute(request);

        } catch (NumberFormatException n) {
            request.setAttribute("error", "User activity update error");
            log.log(Level.DEBUG, "User activity ID parse error", n);
        }
        if (executeStatus) {
            request.setAttribute("actionStatus", "User activity update success");
        } else {
            request.setAttribute("error", "User activity update error");
        }

        request.getRequestDispatcher("get_user_activity_admin_page.command").forward(request, response);
    }

    private boolean updateUserActivityExecute(HttpServletRequest processRequest) throws NumberFormatException {
        UserActivityRepository userActivityRepository  = new UserActivitySQLImpl();
        UserActivityService userActivityService =  new UserActivityService(userActivityRepository);

        UserActivity userActivity = new UserActivity();
        String activityStatus;
        int userActivityId;
        int userId;
        int activityId;

        userActivityId = Optional.ofNullable(processRequest.getParameter("userActivityId"))
                .map(Integer::parseInt)
                .orElse(-1);
        userId = Optional.ofNullable(processRequest.getParameter("userId"))
                .map(Integer::parseInt)
                .orElse(-1);
        activityId = Optional.ofNullable(processRequest.getParameter("activityId"))
                .map(Integer::parseInt)
                .orElse(-1);

        activityStatus = dataCheck(processRequest, "activityStatus");

        if (activityStatus.isEmpty()) {
            log.log(Level.ERROR, "User activity status is empty.");
            return false;
        }

        if (userActivityId < 0 || activityId < 0 || userId < 0) {
            log.log(Level.ERROR, "User Activity update error");
            return false;
        }
        userActivity.setUserActivityId(userActivityId);
        userActivity.setUserId(userId);
        userActivity.setActivityId(activityId);
        userActivity.setActivityStatus(activityStatus);

        processRequest.getSession().setAttribute("userId", userId);
        return userActivityService.updateUserActivity(userActivity);
    }

    private String dataCheck(HttpServletRequest request, String inputParam) {
        return Optional.ofNullable(request.getParameter(inputParam))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }
}

