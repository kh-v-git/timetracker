package com.tracker.impl.admin.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(AddUserAdminPageCommandTest.class)
public class AddUserAdminPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AddUserAdminPageCommand testClass;

    @Test
    public void shouldRedirectPage() throws Exception{
        //
        // Given
        //
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).getRequestDispatcher("pages/admin/user/user_admin_add_new.jsp");
        verify(requestDispatcher).forward(request, response);

    }
}