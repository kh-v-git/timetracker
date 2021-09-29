package com.tracker.security.commands;

import com.tracker.Command;
import com.tracker.impl.common.data.AssignToUserActivity;
import com.tracker.impl.common.data.DataRepository;
import com.tracker.impl.common.data.DataSQLImpl;
import com.tracker.impl.common.data.DataService;
import com.tracker.utils.UserActivityStatusEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class IndexCommand implements Command {
    private static final Logger log = LogManager.getLogger(IndexCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            pageUserProcess(request);
        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on IndexCommand", n);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
    private boolean pageUserProcess (HttpServletRequest processRequest) {
        DataRepository dataRepository = new DataSQLImpl();
        DataService dataService = new DataService(dataRepository);
        int userId;

        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        }catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on IndexCommand", n);
            return false;
        }

        List<String> activityStatusesList = UserActivityStatusEnum.getUserActivityStatuses();
        List<AssignToUserActivity> userActivitiesList = dataService.assignToUserActivityList(userId);
        if (userActivitiesList.isEmpty()) {
            processRequest.setAttribute("actionStatus", "User has no activities");
        }
        processRequest.getSession().setAttribute("userId", userId);
        processRequest.getSession().setAttribute("userActivitiesList", userActivitiesList);
        return false;
    }
}
