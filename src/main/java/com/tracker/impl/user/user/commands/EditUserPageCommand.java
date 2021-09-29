package com.tracker.impl.user.user.commands;

import com.tracker.Command;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditUserPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (pageEditUserProcess(request)) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/user/user_edit.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    private boolean pageEditUserProcess(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        int userId;
        try {
            userId = Integer.parseInt(processRequest.getParameter("userId"));
        } catch (NumberFormatException n) {
            processRequest.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand", n);
            return false;
        }

        User editUser = userService.getUserByID(userId);
        processRequest.getSession().setAttribute("editUser", editUser);
        return true;
    }
}
