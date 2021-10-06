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
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(GetUserAdminMainPageCommand.class)
public class GetUserAdminMainPageCommandTest {

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
    private GetUserAdminMainPageCommand testClass;

    @Test
    public void shouldFindUserListUserAdminPage() throws Exception {
        //
        // Given
        //
        User user = new User();
        user.setUserRole("admin");
        user.setUserId(1);
        user.setUserEmail("root@root.com");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchText")).thenReturn("root");
        when(userService.searchUsers("root")).thenReturn(userList);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("searchText", "root");
        verify(userService, times(1)).searchUsers("root");
        verify(session).setAttribute("searchUsers", userList);
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).getRequestDispatcher("pages/admin/user/user_admin_main.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldNotFindAnyOnUserAdminPage() throws Exception {
        //
        // Given
        //
        whenNew(UserRepositorySQLImpl.class).withNoArguments().thenReturn(userRepository);
        whenNew(UserService.class).withArguments(userRepository).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).getRequestDispatcher("pages/admin/user/user_admin_main.jsp");
        verify(requestDispatcher).forward(request, response);
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(request).setAttribute("error", "No user(s) found");
    }
}