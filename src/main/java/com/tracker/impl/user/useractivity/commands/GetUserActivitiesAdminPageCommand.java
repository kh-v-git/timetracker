package com.tracker.impl.user.useractivity.commands;

import com.tracker.Command;
import com.tracker.impl.common.data.AssignToUserActivity;
import com.tracker.impl.common.data.DataRepository;
import com.tracker.impl.common.data.DataSQLImpl;
import com.tracker.impl.common.data.DataService;

import com.tracker.security.RequiresRole;
import com.tracker.utils.UserActivityStatusEnum;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RequiresRole(UserRolesEnum.ADMIN)
public class GetUserActivitiesAdminPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetUserActivitiesAdminPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            pageActivityUserAdminProcess(request);
        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on UserActivityAdminPageCommand", n);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/user/user_admin_activities.jsp");
        requestDispatcher.forward(request, response);
    }

    private void pageActivityUserAdminProcess(HttpServletRequest processRequest) throws NumberFormatException {
        DataRepository dataRepository = new DataSQLImpl();
        DataService dataService = new DataService(dataRepository);

        int userId = Integer.parseInt(processRequest.getParameter("userId"));

        List<String> activityStatusesList = UserActivityStatusEnum.getUserActivityStatuses();
        List<AssignToUserActivity> userActivitiesList = dataService.assignToUserActivityList(userId);


        if (userActivitiesList.isEmpty()) {
            processRequest.setAttribute("actionStatus", "User has no activities");
        }
        processRequest.getSession().setAttribute("userId", userId);
        processRequest.getSession().setAttribute("userActivitiesList", userActivitiesList);
        processRequest.getSession().setAttribute("UserActivityStatusList", activityStatusesList);
    }
}
