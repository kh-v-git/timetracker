package com.tracker.impl.admin.user;

import com.tracker.Command;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import com.tracker.utils.UserStatusEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RequiresRole(UserRolesEnum.ADMIN)
public class EditUserAdminPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditUserAdminPageCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            pageEditUserProcess(request);
        } catch (NumberFormatException n) {
            request.setAttribute("error", "User ID parse error");
            log.log(Level.DEBUG, "User ID parse error on EditPageCommand", n);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/user/user_admin_edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private void pageEditUserProcess(HttpServletRequest processRequest) throws NumberFormatException {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        int userId = Integer.parseInt(processRequest.getParameter("userId"));
        User editUser = userService.getUserByID(userId);
        List<String> userRoles = UserRolesEnum.getUserRoles();
        List<String> userStatuses = UserStatusEnum.getUserStatuses();
        processRequest.getSession().setAttribute("userRoles", userRoles);
        processRequest.getSession().setAttribute("userStatuses", userStatuses);
        processRequest.getSession().setAttribute("editUser", editUser);
    }
}
