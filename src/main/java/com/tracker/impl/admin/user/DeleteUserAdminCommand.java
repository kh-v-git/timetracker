package com.tracker.impl.admin.user;

import com.tracker.Command;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserAdminCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteUserAdminCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus;
        try {
            executeStatus = deleteUserProcess(request);

        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            executeStatus = false;
            log.log(Level.ERROR, "User ID parse error", n);
        }
        if (executeStatus) {
            request.setAttribute("actionStatus", "Delete user success");
        } else {
            request.setAttribute("actionStatus", "Delete user failed");
            log.log(Level.ERROR, "User delete by ID error");
        }
        request.getRequestDispatcher("get_user_admin_main.command").forward(request, response);
    }

    private boolean deleteUserProcess(HttpServletRequest processRequest) throws NumberFormatException {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        Integer userId = Integer.parseInt(processRequest.getParameter("userId"));
        return userService.deleteUserByID(userId);
    }
}
