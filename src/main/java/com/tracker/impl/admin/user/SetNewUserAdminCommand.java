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

        newUser.setUserFirstName(dataCheck(processRequest, "user_first_name"));
        if (newUser.getUserFirstName().isEmpty()) {
            processRequest.setAttribute("error", "User First name empty.");
            return false;
        }

        newUser.setUserLastName(dataCheck(processRequest, "user_last_name"));
        if (newUser.getUserLastName().isEmpty()) {
            processRequest.setAttribute("error", "User Last name empty.");
            return false;
        }

        newUser.setUserEmail(dataCheck(processRequest, "user_email"));
        if (newUser.getUserEmail().isEmpty()) {
            processRequest.setAttribute("error", "User email empty.");
            return false;
        }

        newUser.setUserPassword(dataCheck(processRequest, "user_password"));
        if (newUser.getUserPassword().isEmpty()) {
            processRequest.setAttribute("error", "User password empty.");
            return false;
        }

        newUser.setUserRole(dataCheck(processRequest, "user_role"));
        if (newUser.getUserRole().isEmpty()) {
            processRequest.setAttribute("error", "User role not set.");
            return false;
        }

        newUser.setUserStatus(dataCheck(processRequest, "user_status"));
        if (newUser.getUserStatus().isEmpty()) {
            processRequest.setAttribute("error", "User status not set.");
            return false;
        }

        newUser.setUserAbout(dataCheck(processRequest, "user_about"));

        return userService.createNewUser(newUser);
    }
    private String dataCheck (HttpServletRequest request, String inputParam) {
        return  Optional.ofNullable(request.getParameter(inputParam))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }
}
