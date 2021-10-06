package com.tracker.security.commands;

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
import java.util.Optional;

public class RegisterCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (userExistCheck(request)) {
            String email = Optional.ofNullable(request.getSession().getAttribute("user_email"))
                    .map(Object::toString)
                    .map(String::trim)
                    .orElse("");
            String id = Optional.ofNullable(request.getSession().getAttribute("userId"))
                    .map(Object::toString)
                    .map(String::trim)
                    .orElse("");
            if (!email.isEmpty() && !id.isEmpty()) {
                response.sendRedirect("login.command");
                request.setAttribute("error", "User with this email exist");
                log.log(Level.DEBUG, "User exists. Relogin");
                return;
            }
        } else {
            if (newUserRegistration(request)) {
                request.setAttribute("actionStatus", "User registered. Login");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/login.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            response.sendRedirect("register.command");

        }
        request.setAttribute("error", "Something goes wrong. Register again");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/user/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean userExistCheck(HttpServletRequest authRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);

        String email = dataCheck(authRequest, "user_email");
        if (email.isEmpty()) {
            return false;
        }

        Optional<User> authUserOptional = userService.authUser(email);

        if (!authUserOptional.isPresent()) {
            return false;
        }
        User authUser = authUserOptional.get();

        if (authUser.getUserEmail().equals(email)) {
            authRequest.getSession().setAttribute("user_email", authUser.getUserEmail());
            authRequest.getSession().setAttribute("userId", authUser.getUserId());
            return true;
        }
        return false;
    }

    private boolean newUserRegistration(HttpServletRequest processRequest) {
        UserRepository userRepository = new UserRepositorySQLImpl();
        UserService userService = new UserService(userRepository);
        User newUser = new User();

        newUser.setUserFirstName(dataCheck(processRequest, "user_first_name"));
        if (newUser.getUserFirstName().isEmpty()) {
            processRequest.setAttribute("error", "User First name empty");
            return false;
        }

        newUser.setUserLastName(dataCheck(processRequest, "user_last_name"));
        if (newUser.getUserLastName().isEmpty()) {
            processRequest.setAttribute("error", "User Last name empty");
            return false;
        }

        newUser.setUserEmail(dataCheck(processRequest, "user_email"));
        if (newUser.getUserEmail().isEmpty()) {
            processRequest.setAttribute("error", "User email empty");
            return false;
        }

        newUser.setUserPassword(dataCheck(processRequest, "user_password"));
        if (newUser.getUserPassword().isEmpty()) {
            processRequest.setAttribute("error", "User password empty");
            return false;
        }

        newUser.setUserRole("user");
        newUser.setUserStatus("active");
        newUser.setUserAbout(dataCheck(processRequest, "user_about"));
        return userService.createNewUser(newUser);
    }

    private String dataCheck(HttpServletRequest request, String inputParam) {
        return Optional.ofNullable(request.getParameter(inputParam))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
    }
}


