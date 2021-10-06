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
import java.util.List;
import java.util.Optional;
@RequiresRole(UserRolesEnum.ADMIN)
public class GetUserAdminMainPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(GetUserAdminMainPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean executeStatus = mainUserProcess(request);

        if (!executeStatus) {
            request.setAttribute("actionStatus", "Data search failed");
            log.log(Level.ERROR, "Data search failed.");
        }
        request.getRequestDispatcher("pages/admin/user/user_admin_main.jsp").forward(request, response);
    }

    private boolean mainUserProcess(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        String searchUsers = Optional.ofNullable(processRequest.getParameter("searchText"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        List<User> users = userService.searchUsers(searchUsers);
        processRequest.getSession().setAttribute("searchUsers", users);
        processRequest.setAttribute("searchText", searchUsers);

        if (users.isEmpty()) {
            processRequest.setAttribute("error", "No user(s) found");
            log.log(Level.DEBUG, "Empty activity list");
            return false;
        }
        return true;
    }
}
