package com.tracker.impl.admin.activity.commands;

import com.tracker.Command;
import com.tracker.impl.admin.activity.*;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@RequiresRole(UserRolesEnum.ADMIN)
public class DeleteActivityCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteActivityCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActivityRepository activityRepository = new ActivityRepositorySQLImpl();
        ActivityService activityService = new ActivityService(activityRepository);

        int editActID;
        boolean deleteStatus = false;
        try{
            editActID = Integer.parseInt(request.getParameter("activityId"));
            deleteStatus = activityService.deleteActivity(editActID);
        }catch (NumberFormatException n) {
            request.setAttribute("error", "Activity ID parse error");
            log.log(Level.ERROR, "Activity ID parse error", n);
        }
        if (!deleteStatus) {
            request.setAttribute("actionStatus", "Delete process failed");
            log.log(Level.ERROR, "Activity delete process failed. No Id found");
        } else {
            request.setAttribute("actionStatus", "Delete activity success");
        }
        request.getRequestDispatcher("get_activities_main.command").forward(request, response);
    }
}
