package com.tracker.impl.admin.activity.commands;

import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(SetNewActivityCommand.class)
public class SetNewActivityCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ActivityService activityService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ActivityRepositorySQLImpl activityRepository;

    @InjectMocks
    private SetNewActivityCommand testClass;

    @Test
    public void shouldSetNewActivity() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("actNewName")).thenReturn("testName");
        when(request.getParameter("actNewDescription")).thenReturn("testDescription");
        when(activityService.setActivity(any())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(activityService, times(1)).setActivity(any());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Activity add success");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");

    }

    @Test
    public void shouldThrowParseIDError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("sdadfa");
        when(request.getParameter("actNewName")).thenReturn("testName");
        when(request.getParameter("actNewDescription")).thenReturn("testDescription");
        when(activityService.setActivity(any())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "Category ID parse error");
        verify(request).setAttribute("actionStatus", "Activity add failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");

    }
}