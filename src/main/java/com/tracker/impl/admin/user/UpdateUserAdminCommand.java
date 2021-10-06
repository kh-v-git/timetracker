package com.tracker.impl.admin.user;

import com.tracker.Command;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiresRole(UserRolesEnum.ADMIN)
public class UpdateUserAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateUserAdminCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = false;

        executeStatus = pageUpdateUserProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Update user success");
        } else {
            request.setAttribute("actionStatus", "Update user failed");
            log.log(Level.ERROR, "User update error on EditPageCommand");
        }

        request.getRequestDispatcher("get_user_admin_main.command").forward(request, response);
    }

    private boolean pageUpdateUserProcess(HttpServletRequest processRequest) {
        User updateUser = new User();
        Integer userId;
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        try {
            userId = Integer.parseInt(processRequest.getParameter("userId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User update error");
            log.log(Level.DEBUG, "User update error on EditPageCommand", n);
            return false;
        }
        updateUser.setUserId(userId);

        updateUser.setUserFirstName(Optional.ofNullable(processRequest.getParameter("user_first_name"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserFirstName().isEmpty()) {
            processRequest.setAttribute("error", "User First name empty");
            return false;
        }

        updateUser.setUserLastName(Optional.ofNullable(processRequest.getParameter("user_last_name"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserLastName().isEmpty()) {
            processRequest.setAttribute("error", "User Last name empty");
            return false;
        }

        updateUser.setUserEmail(Optional.ofNullable(processRequest.getParameter("user_email"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserEmail().isEmpty()) {
            processRequest.setAttribute("error", "User email empty");
            return false;
        }

        updateUser.setUserPassword(Optional.ofNullable(processRequest.getParameter("user_password"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserPassword().isEmpty()) {
            processRequest.setAttribute("error", "User password empty");
            return false;
        }

        updateUser.setUserRole(Optional.ofNullable(processRequest.getParameter("user_role"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserRole().isEmpty()) {
            processRequest.setAttribute("error", "User role not set");
            return false;
        }

        updateUser.setUserStatus(Optional.ofNullable(processRequest.getParameter("user_status"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (updateUser.getUserStatus().isEmpty()) {
            processRequest.setAttribute("error", "User status not set");
            return false;
        }

        updateUser.setUserAbout(Optional.ofNullable(processRequest.getParameter("user_about"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));

        return userService.updateUserByID(updateUser);
    }
}
