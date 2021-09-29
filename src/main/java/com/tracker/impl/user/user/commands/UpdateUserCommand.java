package com.tracker.impl.user.user.commands;

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
@RequiresRole(UserRolesEnum.USER)
public class UpdateUserCommand implements Command {
    private static final Logger log = LogManager.getLogger(UpdateUserCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = updateUserProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Update user success");
        } else {
            request.setAttribute("error", "Data incorrect");
            request.setAttribute("actionStatus", "Update user failed");
            log.log(Level.ERROR, "User update error on EditPageCommand");
        }
        request.getRequestDispatcher("index.command").forward(request, response);
    }
    private boolean updateUserProcess(HttpServletRequest processRequest) {
        User updateUser = new User();
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);
        int userId;

        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        } catch (NumberFormatException n) {
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand", n);
            return false;
        }

        updateUser.setUserFirstName(dataCheck(processRequest, "user_first_name"));

        if (updateUser.getUserFirstName().isEmpty()) {
            processRequest.setAttribute("error", "User First name empty.");
            return false;
        }

        updateUser.setUserLastName(dataCheck(processRequest,"user_last_name"));
        if (updateUser.getUserLastName().isEmpty()) {
            processRequest.setAttribute("error", "User Last name empty.");
            return false;
        }

        updateUser.setUserEmail(dataCheck(processRequest, "user_email"));
        if (updateUser.getUserEmail().isEmpty()) {
            processRequest.setAttribute("error", "User email empty.");
            return false;
        }

        updateUser.setUserPassword(dataCheck(processRequest,"user_password"));
        if (updateUser.getUserPassword().isEmpty()) {
            processRequest.setAttribute("error", "User password empty.");
            return false;
        }

        updateUser.setUserRole(processRequest.getSession().getAttribute("userRole").toString());
        if (updateUser.getUserRole().isEmpty()) {
            processRequest.setAttribute("error", "User role not set.");
            return false;
        }

        updateUser.setUserStatus(processRequest.getSession().getAttribute("userStatus").toString());
        if (updateUser.getUserStatus().isEmpty()) {
            processRequest.setAttribute("error", "User status not set.");
            return false;
        }
        updateUser.setUserAbout(dataCheck(processRequest, "user_about"));
        updateUser.setUserId(userId);
        return userService.updateUserByID(updateUser);
    }
    private String dataCheck(HttpServletRequest req, String param) {
       return Optional.ofNullable(req.getParameter(param))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }

}
