package com.tracker.impl.user.user.commands;

import com.tracker.Command;
import com.tracker.impl.user.useractivity.UserActivity;
import com.tracker.impl.user.useractivity.UserActivityRepository;
import com.tracker.impl.user.useractivity.UserActivitySQLImpl;
import com.tracker.impl.user.useractivity.UserActivityService;
import com.tracker.impl.user.useractivitylog.UserActivityTimeLog;
import com.tracker.impl.user.useractivitylog.UserActivityTimeLogRepository;
import com.tracker.impl.user.useractivitylog.UserActivityTimeLogSQLImpl;
import com.tracker.impl.user.useractivitylog.UserActivityTimeLogService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

public class LogNewUserActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogNewUserActivityCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userActivityLogProcess(request)) {
            request.setAttribute("actionStatus", "Activity log success");
        } else {
            request.setAttribute("actionStatus", "Activity log failed");
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand");
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.command");
        requestDispatcher.forward(request, response);
    }

    private boolean userActivityLogProcess(HttpServletRequest processRequest) {
        UserActivityRepository userActivityRepository = new UserActivitySQLImpl();
        UserActivityService userActivityService = new UserActivityService(userActivityRepository);
        UserActivityTimeLogRepository userActivityTimeLogRepository = new UserActivityTimeLogSQLImpl();
        UserActivityTimeLogService userActivityTimeLogService = new UserActivityTimeLogService(userActivityTimeLogRepository);
        int userId;
        int activityId;
        int timeLog;
        LocalDate startDate;

        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on Log new activity", n);
            return false;
        }
        try {
            activityId = Integer.parseInt(processRequest.getParameter("activityId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "Activity ID parse error");
            log.log(Level.DEBUG, "Activity ID parse error on Log new activity", n);
            return false;
        }

        try {
            timeLog = Integer.parseInt(processRequest.getParameter("timeLog"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "TimeLog parse error");
            log.log(Level.DEBUG, "TimeLog parse error on Log new activity", n);
            return false;
        }
        startDate = LocalDate.parse(processRequest.getParameter("startDate"));

        List<UserActivity> userActivityList = userActivityService.getUserActivityList();
        UserActivity getUserActivity = userActivityList.stream()
                .filter(userActivity -> userActivity.getUserId() == userId)
                .filter(userActivity -> userActivity.getActivityId() == activityId)
                .findFirst().orElse(null);

        if (getUserActivity == null) {
            processRequest.setAttribute("error", "No accepted user activity");
            log.log(Level.DEBUG, "No accepted user activity");
            return false;
        }

        UserActivityTimeLog userActivityTimeLog = new UserActivityTimeLog();
        userActivityTimeLog.setUserActivityId(getUserActivity.getUserActivityId());
        userActivityTimeLog.setActivityTimeLog(timeLog);
        userActivityTimeLog.setActivityStartDate(startDate);

        return userActivityTimeLogService.setUserActivityTimeLog(userActivityTimeLog);
    }
}
