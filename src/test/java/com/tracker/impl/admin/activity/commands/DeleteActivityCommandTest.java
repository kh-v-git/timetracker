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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(DeleteActivityCommand.class)
public class DeleteActivityCommandTest {

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
    private DeleteActivityCommand testClass;

    @Test
    public void shouldDeleteActivity() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getParameter("activityId")).thenReturn("1");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(activityService.deleteActivity(anyInt())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(activityService, times(1)).deleteActivity(anyInt());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Delete activity success");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }

    @Test
    public void shouldThrowParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getParameter("activityId")).thenReturn("sdfadf");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(activityService.deleteActivity(anyInt())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "Activity ID parse error");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }

    @Test
    public void shouldSetFailedActivity() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getParameter("activityId")).thenReturn("1");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(activityService.deleteActivity(anyInt())).thenReturn(false);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(activityService, times(1)).deleteActivity(anyInt());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Delete process failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }
}