package com.tracker.user.commands;

import com.tracker.Command;
import com.tracker.user.User;
import com.tracker.user.UserRepository;
import com.tracker.user.UserRepositorySQLImpl;
import com.tracker.user.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SetNewUserAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(SetNewUserAdminCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean executeStatus = addUserProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Set new user success.");
        } else {
            request.setAttribute("actionStatus", "User add failed");
            log.log(Level.ERROR, "Category ID parse error");
        }
        request.getRequestDispatcher("get_user_admin_main.command").forward(request, response);
    }

    private boolean addUserProcess(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);
        User newUser = new User();


        newUser.setUserFirstName(Optional.ofNullable(processRequest.getParameter("user_first_name"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserFirstName().isEmpty()) {
            processRequest.setAttribute("error", "User First name empty.");
            return false;
        }

        newUser.setUserLastName(Optional.ofNullable(processRequest.getParameter("user_last_name"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserLastName().isEmpty()) {
            processRequest.setAttribute("error", "User Last name empty.");
            return false;
        }

        newUser.setUserEmail(Optional.ofNullable(processRequest.getParameter("user_email"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserEmail().isEmpty()) {
            processRequest.setAttribute("error", "User email empty.");
            return false;
        }

        newUser.setUserPassword(Optional.ofNullable(processRequest.getParameter("user_password"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserPassword().isEmpty()) {
            processRequest.setAttribute("error", "User password empty.");
            return false;
        }

        newUser.setUserRole(Optional.ofNullable(processRequest.getParameter("user_role"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserRole().isEmpty()) {
            processRequest.setAttribute("error", "User role not set.");
            return false;
        }

        newUser.setUserStatus(Optional.ofNullable(processRequest.getParameter("user_status"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));
        if (newUser.getUserStatus().isEmpty()) {
            processRequest.setAttribute("error", "User status not set.");
            return false;
        }

        newUser.setUserAbout(Optional.ofNullable(processRequest.getParameter("user_about"))
                .map(Object::toString)
                .map(String::trim)
                .orElse(""));

        return userService.createNewUser(newUser);
    }

}
