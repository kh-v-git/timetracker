package com.tracker.impl.admin.report;

import com.tracker.Command;
import com.tracker.impl.common.data.*;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserActivityStatusEnum;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@RequiresRole(UserRolesEnum.ADMIN)
public class GetAdminReportPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetAdminReportPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pageAdminReportProcess(request);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/report/admin_report.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean pageAdminReportProcess(HttpServletRequest processRequest) {
        DataRepository dataRepository = new DataSQLImpl();
        DataService dataService = new DataService(dataRepository);
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        List<ActivityUserTimeCount> activityCountList = new ArrayList<>();

        List<User> userList = userService.searchUsers("");
        for(User user:userList) {
            List<AssignToUserActivity> userActivitiesList = dataService.assignToUserActivityList(user.getUserId());
            for (AssignToUserActivity activity:userActivitiesList) {
                boolean findActivity = false;
                int actId = activity.getActivityId();
                for (ActivityUserTimeCount timeCount:activityCountList) {
                    if (actId == timeCount.getActivityId()) {
                        int time = timeCount.getActivityTimeTotalLogged();
                        int users = timeCount.getUsersActivityCount();
                        timeCount.setUsersActivityCount(users + 1);
                        timeCount.setActivityTimeTotalLogged(time + activity.getActivityTimeLog());
                        findActivity = true;
                    }else {
                        findActivity = false;
                    }
                }
                if (!findActivity) {
                    ActivityUserTimeCount newTimeCount = new ActivityUserTimeCount();
                    newTimeCount.setUserId(activity.getUserId());
                    newTimeCount.setActivityId(activity.getActivityId());
                    newTimeCount.setActivityName(activity.getActivityName());
                    newTimeCount.setUsersActivityCount(1);
                    newTimeCount.setActivityTimeTotalLogged(activity.getTimeLogId());
                    activityCountList.add(newTimeCount);
                }
            }
        }


        if (activityCountList.isEmpty()) {
            processRequest.setAttribute("actionStatus", "No activities");
        }
        processRequest.setAttribute("activityCountList", activityCountList);
        return false;
    }
}
