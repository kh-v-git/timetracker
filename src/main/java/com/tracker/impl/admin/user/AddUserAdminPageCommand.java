package com.tracker.impl.admin.user;

import com.tracker.Command;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import com.tracker.utils.UserStatusEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@RequiresRole(UserRolesEnum.ADMIN)
public class AddUserAdminPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<String> userRoles = UserRolesEnum.getUserRoles();
        List<String> userStatuses = UserStatusEnum.getUserStatuses();
        request.getSession().setAttribute("userRoles", userRoles);
        request.getSession().setAttribute("userStatuses", userStatuses);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/user/user_admin_add_new.jsp");
        requestDispatcher.forward(request, response);
    }
}
