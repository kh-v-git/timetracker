package com.tracker.impl.user.useractivity.commands;

import com.tracker.Command;
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
public class DeleteUserActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteUserActivityCommand.class);
    private UserActivityService userActivityService;
    private UserActivityRepository userActivityRepository;

    @Override

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = false;
        try {
            executeStatus = deleteUserActivityExecute(request);

        } catch (NumberFormatException n) {
            request.setAttribute("error", "User activity ID parse error");
            log.log(Level.DEBUG, "User activity ID parse error", n);
        }
        if (executeStatus) {
            request.setAttribute("actionStatus", "User activity delete success");
        }

        request.getRequestDispatcher("get_user_activity_admin_page.command").forward(request, response);
    }

    private boolean deleteUserActivityExecute(HttpServletRequest processRequest) throws NumberFormatException {
        userActivityRepository = new UserActivitySQLImpl();
        userActivityService = new UserActivityService(userActivityRepository);

        Integer userActivityId = Optional.ofNullable(processRequest.getParameter("userActivityId"))
                .map(Integer::parseInt)
                .orElse(-1);
        int userId = Integer.parseInt(processRequest.getParameter("userId"));


        if (userActivityId < 0) {
            processRequest.setAttribute("error", "UserActivityID parse error");
            log.log(Level.ERROR, "UserActivityID parse error");
            return false;
        }
        processRequest.getSession().setAttribute("userId", userId);
        return userActivityService.deleteUserActivity(userActivityId);
    }
}
