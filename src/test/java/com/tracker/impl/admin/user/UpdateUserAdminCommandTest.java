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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(UpdateUserAdminCommand.class)
public class UpdateUserAdminCommandTest {

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
    private UpdateUserAdminCommand testClass;

    @Test
    public void shouldUpdateUserData() throws Exception {
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

        testClass.execute(request, response);

        verify(request).getRequestDispatcher("get_user_admin_main.command");
        verify(requestDispatcher).forward(request, response);
        verify(userService).updateUserByID(userCaptor.capture());
        User resultUser = userCaptor.getValue();
        assertThat(resultUser.getUserAbout(), is("newNew"));
    }

}