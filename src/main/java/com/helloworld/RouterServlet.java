package com.helloworld;

import com.helloworld.admin.category.commands.GetCategoriesListCommand;
import com.helloworld.commands.IndexCommand;
import com.helloworld.commands.SecuredUserCommand;
import com.helloworld.security.commands.AuthenticateCommand;
import com.helloworld.security.commands.LoginCommand;
import com.helloworld.security.commands.LogoutCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@WebServlet({"*.command"})
public class RouterServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(RouterServlet.class);

    static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("/login.command", new LoginCommand());
        commands.put("/authenticate.command", new AuthenticateCommand());
        commands.put("/secured.command", new SecuredUserCommand());
        commands.put("/logout.command", new LogoutCommand());
        commands.put("/index.command", new IndexCommand());
        commands.put("/getCategoriesList.command", new GetCategoriesListCommand());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String commandName = request.getRequestURI().substring(request.getContextPath().length());

        if (commandName.equals("/") || commandName.isEmpty()) {
            commandName = "/index.command";
        }

        log.debug("Processing command: {}", commandName);

        if (commands.containsKey(commandName)) {
            Command command = commands.get(commandName);
            try {
                command.execute(request, response);
            } catch (Exception e) {
                log.error("Error in RouterServlet commands", e );
                throw new ServletException("Failed to execute command", e);
            }
        } else {
            log.error("Command not found in RouterServlet commands");
            throw new ServletException("Command not found");
        }
    }

}
