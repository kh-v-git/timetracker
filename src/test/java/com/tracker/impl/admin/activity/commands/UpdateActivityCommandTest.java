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
@PrepareForTest(UpdateActivityCommand.class)
public class UpdateActivityCommandTest {
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
    private UpdateActivityCommand testClass;

    @Test
    public void shouldUpdateActivity() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("activityId")).thenReturn("2");
        when(request.getParameter("actNewName")).thenReturn("testName");
        when(request.getParameter("actNewDescription")).thenReturn("testDescription");
        when(activityService.updateActivity(any())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(activityService, times(1)).updateActivity(any());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Activity update done");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }

    @Test
    public void shouldLogCategoryIdParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("ssdffd");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("actionStatus", "Activity update error");
        verify(request).setAttribute("error", "Category ID parse error");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }

    @Test
    public void shouldLogActivityIdParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("activityId")).thenReturn("asdf");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("actionStatus", "Activity update error");
        verify(request).setAttribute("error", "Activity ID parse error");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }

    @Test
    public void shouldLogEmptyDataError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("activityId")).thenReturn("2");
        when(request.getParameter("actNewName")).thenReturn("");
        when(request.getParameter("actNewDescription")).thenReturn("");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("actionStatus", "Activity update error");
        verify(request).setAttribute("error", "Data empty");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("get_activities_main.command");
    }
}