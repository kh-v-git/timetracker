package com.tracker.impl.admin.user;

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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(SetNewUserAdminCommand.class)
public class SetNewUserAdminCommandTest {

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

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private SetNewUserAdminCommand testClass;

    @Test
    public void shouldSetUpNewUser() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        when(request.getParameter("user_email")).thenReturn("kh@kh.com");
        when(request.getParameter("user_password")).thenReturn("123456");
        when(request.getParameter("user_role")).thenReturn("admin");
        when(request.getParameter("user_status")).thenReturn("new");
        when(request.getParameter("user_about")).thenReturn("newNew");
        when(userService.createNewUser(userCaptor.capture())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
        verify(userService).createNewUser(userCaptor.capture());
        User resultUser = userCaptor.getValue();
        assertThat(resultUser.getUserAbout(), is("newNew"));
        verify(request).setAttribute("actionStatus", "Set new user success");
    }

    @Test
    public void shouldCheckUserFirstNameInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User First name empty");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckUserLastNameInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");

        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User Last name empty");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckUserEmailInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User email empty");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckUserPasswordInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        when(request.getParameter("user_email")).thenReturn("kh@kh.com");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User password empty");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckUserRoleInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        when(request.getParameter("user_email")).thenReturn("kh@kh.com");
        when(request.getParameter("user_password")).thenReturn("123456");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User role not set");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCheckUserStatusInput() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        when(request.getParameter("user_email")).thenReturn("kh@kh.com");
        when(request.getParameter("user_password")).thenReturn("123456");
        when(request.getParameter("user_role")).thenReturn("admin");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "User status not set");
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldUpdateUserSuccess() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("user_first_name")).thenReturn("testUser");
        when(request.getParameter("user_last_name")).thenReturn("testLast");
        when(request.getParameter("user_email")).thenReturn("kh@kh.com");
        when(request.getParameter("user_password")).thenReturn("123456");
        when(request.getParameter("user_role")).thenReturn("admin");
        when(request.getParameter("user_status")).thenReturn("new");
        when(request.getParameter("user_about")).thenReturn("newNew");
        when(userService.updateUserByID(userCaptor.capture())).thenReturn(false);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute("actionStatus", "User add failed");
    }
}