package com.tracker.security.commands;

import com.tracker.Command;
import com.tracker.impl.common.data.AssignToUserActivity;
import com.tracker.impl.common.data.DataRepository;
import com.tracker.impl.common.data.DataSQLImpl;
import com.tracker.impl.common.data.DataService;
import com.tracker.impl.user.useractivity.commands.GetUserActivitiesAdminPageCommand;
import com.tracker.utils.UserActivityStatusEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RegisterUserPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegisterUserPageCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/user/registration.jsp");
        requestDispatcher.forward(request, response);
    }

}
