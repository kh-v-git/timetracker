package com.tracker.impl.user.user.commands;

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

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiresRole(UserRolesEnum.USER)
public class RequestUserActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(RequestUserActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (requestActivityProcess(request)) {
            request.setAttribute("actionStatus", "User activity request success");
        } else {
            request.setAttribute("actionStatus", "User activity request failed");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.command");
        requestDispatcher.forward(request, response);
    }

    private boolean requestActivityProcess(HttpServletRequest processRequest) {
        UserActivityRepository userActivityRepository = new UserActivitySQLImpl();
        UserActivityService userActivityService = new UserActivityService(userActivityRepository);
        UserActivity newUserActivity = new UserActivity();
        int userId;
        int activityId;
        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.ERROR, "User ID parse error", n);
            return false;
        }
        try {
            activityId = Integer.parseInt(processRequest.getParameter("activityId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User Activity ID parse error");
            log.log(Level.ERROR, "User Activity ID parse error", n);
            return false;
        }

        newUserActivity.setUserId(userId);
        newUserActivity.setActivityId(activityId);
        newUserActivity.setActivityStatus("requested");
        return userActivityService.setUserActivity(newUserActivity);
    }
}
