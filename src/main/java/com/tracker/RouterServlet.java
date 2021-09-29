package com.tracker;

import com.tracker.impl.admin.activity.commands.*;
import com.tracker.impl.admin.activity.commands.UpdateActivityCommand;
import com.tracker.impl.admin.category.commands.*;
import com.tracker.impl.admin.report.GetAdminReportPageCommand;
import com.tracker.impl.admin.user.*;
import com.tracker.security.commands.IndexCommand;
import com.tracker.security.commands.SecuredUserCommand;
import com.tracker.security.commands.AuthenticateCommand;
import com.tracker.security.commands.LoginCommand;
import com.tracker.security.commands.LogoutCommand;
import com.tracker.security.commands.RegisterUserPageCommand;
import com.tracker.impl.user.user.commands.*;
import com.tracker.impl.user.useractivity.commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@WebServlet({"*.command"})
public class RouterServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RouterServlet.class);

    static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("/login.command", new LoginCommand());
        commands.put("/register_user_page.command", new RegisterUserPageCommand());
        commands.put("/authenticate.command", new AuthenticateCommand());
        commands.put("/secured.command", new SecuredUserCommand());
        commands.put("/logout.command", new LogoutCommand());
        commands.put("/index.command", new IndexCommand());
        commands.put("/get_categories_main.command", new GetCategoriesMainPageCommand());
        commands.put("/get_category_page.command", new GetCategoryAddNewPageCommand());
        commands.put("/add_new_category.command", new SetNewCategoryCommand());
        commands.put("/edit_category_page.command", new EditCategoryPageCommand());
        commands.put("/delete_category.command", new DeleteCategoryCommand());
        commands.put("/update_category.command", new UpdateCategoryCommand());
        commands.put("/get_activities_main.command", new GetActivitiesMainPageCommand());
        commands.put("/get_new_activity_page.command", new GetNewActivityPageCommand());
        commands.put("/add_new_activity.command", new SetNewActivityCommand());
        commands.put("/edit_activity_page.command", new EditActivityPageCommand());
        commands.put("/delete_activity.command", new DeleteActivityCommand());
        commands.put("/update_activity.command", new UpdateActivityCommand());
        commands.put("/get_user_admin_main.command", new GetUserAdminMainPageCommand());
        commands.put("/get_user_admin_page.command", new AddUserAdminPageCommand());
        commands.put("/set_new_user_admin.command", new SetNewUserAdminCommand());
        commands.put("/edit_user_admin_page.command", new EditUserAdminPageCommand());
        commands.put("/delete_user_admin.command", new DeleteUserAdminCommand());
        commands.put("/update_user_admin.command", new UpdateUserAdminCommand());
        commands.put("/get_user_activity_admin_page.command", new GetUserActivitiesAdminPageCommand());
        commands.put("/set_new_user_activity_admin_page.command", new GetNewUserActivityAdminPageCommand());
        commands.put("/add_new_activity_user_admin.command", new SetNewUserActivityCommand());
        commands.put("/edit_user_activity_admin_page.command", new EditUserActivityAdminPageCommand());
        commands.put("/delete_user_activity.command", new DeleteUserActivityCommand());
        commands.put("/update_user_activity.command", new UpdateUserActivityCommand());
        commands.put("/edit_user_page.command", new EditUserPageCommand());
        commands.put("/update_user.command", new UpdateUserCommand());
        commands.put("/delete_user.command", new DeleteUserCommand());
        commands.put("/request_activity_user_page.command", new RequestUserActivityPageCommand());
        commands.put("/request_new_activity_user.command", new RequestUserActivityCommand());
        commands.put("/log_activity_user_page.command", new LogNewActivityUserPageCommand());
        commands.put("/log_new_activity_user.command", new LogNewUserActivityCommand());
        commands.put("/get_admin_report_page.command", new GetAdminReportPageCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String commandName = request.getRequestURI().substring(request.getContextPath().length());

        if (commandName.equals("/") || commandName.isEmpty()) {
            commandName = "/index.command";
        }

        log.debug("Processing command: {}", commandName);

        if (commands.containsKey(commandName)) {
            Command command = commands.get(commandName);
            try {
                command.execute(request, response);
            } catch (Exception e) {
                log.error("Error in RouterServlet commands", e );
                throw new ServletException("Failed to execute command", e);
            }
        } else {
            log.error("Command not found in RouterServlet commands");
            throw new ServletException("Command not found");
        }
    }

}
