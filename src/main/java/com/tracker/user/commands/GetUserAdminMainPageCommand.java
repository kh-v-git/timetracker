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
import java.util.List;
import java.util.Optional;

public class GetUserAdminMainPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetUserAdminMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = false;
        try {
            executeStatus = mainUserProcess(request);

        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            executeStatus = false;
            log.log(Level.ERROR, "User ID parse error", n);
        }
        if (!executeStatus) {
            request.setAttribute("actionStatus", "Data search failed.");
            log.log(Level.ERROR, "User ID parse error");
        }
        request.getRequestDispatcher("pages/admin/user/user_admin_main.jsp").forward(request, response);
    }

    private boolean mainUserProcess(HttpServletRequest processRequest) throws NumberFormatException {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        String searchAct = Optional.ofNullable(processRequest.getParameter("searchText"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        List<User> users = userService.searchUsers(searchAct);
        processRequest.getSession().setAttribute("searchUsers", users);
        processRequest.setAttribute("searchText", searchAct);
        if (users.isEmpty()) {
            processRequest.setAttribute("error", "No activities found");
            log.log(Level.DEBUG, "Empty activity list");
        }
        return true;
    }
}
