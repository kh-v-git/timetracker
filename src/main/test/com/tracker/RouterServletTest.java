package com.tracker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RouterServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private RouterServlet sut;

    @Test
    public void shouldExecuteCommand() throws Exception {
        when(request.getRequestURI()).thenReturn("/timetracker/login.command");
        when(request.getContextPath()).thenReturn("/timetracker");
        when(request.getRequestDispatcher("pages/login.jsp")).thenReturn(requestDispatcher);

        sut.doGet(request, response);

        verify(request).getRequestDispatcher("pages/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}