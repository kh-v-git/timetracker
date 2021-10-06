package com.tracker.impl.admin.user;

import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.impl.admin.category.commands.UpdateCategoryCommand;
import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(DeleteUserAdminCommand.class)
public class DeleteUserAdminCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UserService userService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserRepositorySQLImpl userRepository;


    @InjectMocks
    private DeleteUserAdminCommand testClass;

    @Test
    public void shouldDeleteUserCommand() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(userService.deleteUserByID(1)).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(userService, times(1)).deleteUserByID(1);
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(request).setAttribute("actionStatus", "Delete user success");
        verify(request, never()).setAttribute(eq("error"), any());
    }

    @Test
    public void shouldFailedDeleteUserCommand() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(userService.deleteUserByID(1)).thenReturn(false);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(userService, times(1)).deleteUserByID(1);
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(request).setAttribute("actionStatus", "Delete user failed");
        verify(request, never()).setAttribute(eq("error"), any());
    }

    @Test
    public void shouldFailedParseIDUserCommand() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("asdf");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(request).setAttribute("error", "User ID parse error");
        verify(request).setAttribute("actionStatus", "Delete user failed");
    }
}