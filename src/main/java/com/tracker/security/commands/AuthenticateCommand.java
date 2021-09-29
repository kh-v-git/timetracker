package com.tracker.security.commands;

import com.tracker.Command;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepository;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthenticateCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (authorizationCheck(request)) {
            if (request.getSession().getAttribute("userRole").equals("admin")) {
                response.sendRedirect("secured.command");
                return;
            }
            if (request.getSession().getAttribute("userRole").equals("user")) {
                response.sendRedirect("index.command");
                return;
            }
        }
        request.setAttribute("error", "Wrong username or password");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/login.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean authorizationCheck(HttpServletRequest authRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        String email = dataCheck(authRequest, "user_email");
        String password = dataCheck(authRequest, "user_password");
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }

        Optional<User> authUserOptional = userService.authUser(email);

        if (!authUserOptional.isPresent()) {
            return false;
        }
        User authUser = authUserOptional.get();

        if (authUser.getUserEmail().equals(email) && authUser.getUserPassword().equals(password)) {
            authRequest.getSession().setAttribute("userRole", authUser.getUserRole());
            authRequest.getSession().setAttribute("userStatus", authUser.getUserStatus());
            authRequest.getSession().setAttribute("user_email", authUser.getUserEmail());
            authRequest.getSession().setAttribute("userId", authUser.getUserId());
            return true;
        }
        return false;
    }

    private String dataCheck(HttpServletRequest request, String inputParam) {
        return Optional.ofNullable(request.getParameter(inputParam))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }
}
