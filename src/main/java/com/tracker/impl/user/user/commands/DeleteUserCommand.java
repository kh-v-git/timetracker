package com.tracker.impl.user.user.commands;

import com.tracker.Command;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteUserCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = deleteUserProcess(request);

        if (executeStatus) {
            request.setAttribute("actionStatus", "Delete user success");
        } else {
            request.setAttribute("error", "Data incorrect");
            request.setAttribute("actionStatus", "Delete user failed");
            log.log(Level.ERROR, "User delete error on DeleteUserPageCommand");
        }
        request.getRequestDispatcher("pages/login.jsp").forward(request, response);

    }
    private boolean deleteUserProcess(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);
        int userId;
        try {
            userId = Integer.parseInt(processRequest.getSession().getAttribute("userId").toString());
        } catch (NumberFormatException n) {
            log.log(Level.DEBUG, "User ID parse error on DeletePageCommand", n);
            return false;
        }
        userService.deleteUserByID(userId);
        return true;
    }
}
