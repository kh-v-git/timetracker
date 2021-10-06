package com.tracker.impl.admin.user;

import com.tracker.impl.user.user.User;
import com.tracker.impl.user.user.UserRepositorySQLImpl;
import com.tracker.impl.user.user.UserService;

import com.tracker.utils.UserRolesEnum;
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
import javax.servlet.http.HttpSession;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(EditUserAdminPageCommand.class)
public class EditUserAdminPageCommandTest {

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

    @Mock
    private HttpSession session;

    @InjectMocks
    private EditUserAdminPageCommand testClass;


    @Test
    public void shouldUserForAdminPage() throws Exception {
        //
        // Given
        //
        User user = new User();
        user.setUserRole("admin");
        user.setUserId(1);
        user.setUserEmail("root@root.com");
        List<String> statusList = UserRolesEnum.getUserRoles();
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userId")).thenReturn("1");
        when(userService.getUserByID(1)).thenReturn(user);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(session).setAttribute("userRoles", statusList);
        verify(userService, times(1)).getUserByID(1);
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Data search done");
        verify(request).getRequestDispatcher("pages/admin/user/user_admin_edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldFailParseUserIDAdminPage() throws Exception {
        //
        // Given
        //
        User user = new User();
        user.setUserRole("admin");
        user.setUserId(1);
        user.setUserEmail("root@root.com");
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("userId")).thenReturn("asd");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User ID parse error");
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(request).getRequestDispatcher("pages/admin/user/user_admin_edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}