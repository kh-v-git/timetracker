package com.tracker.impl.admin.activity.commands;

import com.tracker.impl.admin.activity.Activity;
import com.tracker.impl.admin.activity.ActivityRepositorySQLImpl;
import com.tracker.impl.admin.activity.ActivityService;
import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import org.junit.Before;
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
@PrepareForTest(GetActivitiesMainPageCommand.class)
public class GetActivitiesMainPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ActivityService activityService;

    @Mock
    private ActivityRepositorySQLImpl activityRepository;

    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryRepositorySQLImpl categoryRepository;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @InjectMocks
    private GetActivitiesMainPageCommand testClass;

    private List<Category> categoryList;
    private List<Activity> activityList;
    @Before
    public void setUP() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("test");
        categoryList = new ArrayList<>();
        categoryList.add(category);
        Activity activity = new Activity();
        activity.setActivityId(1);
        activity.setActivityName("tset");
        activityList = new ArrayList<>();
        activityList.add(activity);
    }

    @Test
    public void shouldRedirectToActivityMainPageWithData() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchCategoryId")).thenReturn("1");
        when(request.getParameter("search-category")).thenReturn("asdf");
        when(request.getParameter("searchText")).thenReturn("fdsa");
        when(categoryService.searchCategories(any())).thenReturn(categoryList);
        when(activityService.searchActivityList("fdsa", 1)).thenReturn(activityList);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(categoryService, times(1)).searchCategories(any());
        verify(activityService, times(1)).searchActivityList(any(), eq(1));
        verify(session).setAttribute("searchCategories", categoryList);
        verify(session).setAttribute("searchActivities", activityList);
        verify(session).setAttribute("searchCategoryId", 1);
        verify(request).setAttribute("searchText", "fdsa");
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Data search done");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_main.jsp");
    }

    @Test
    public void shouldLogCategoryIDParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchCategoryId")).thenReturn("die");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //

        verify(request).setAttribute("error", "Category ID parse error");
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_main.jsp");
    }

    @Test
    public void shouldCheckEmptyLists() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchCategoryId")).thenReturn("1");
        categoryList = new ArrayList<>();
        activityList = new ArrayList<>();
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //

        verify(request).setAttribute("error", "No data found");
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_main.jsp");
    }

}