package com.tracker.impl.admin.user;

import com.tracker.Command;
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

@RequiresRole(UserRolesEnum.ADMIN)
public class DeleteUserAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteUserAdminCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean executeStatus = deleteUserProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Delete user success");
        } else {
            request.setAttribute("actionStatus", "Delete user failed");
            log.log(Level.ERROR, "Delete user failed");
        }
        request.getRequestDispatcher("get_user_admin_main.command").forward(request, response);
    }

    private boolean deleteUserProcess(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);
        Integer userId;

        try {
            userId = Integer.parseInt(processRequest.getParameter("userId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.ERROR, "User ID parse error", n);
            return false;
        }
        return userService.deleteUserByID(userId);
    }
}
