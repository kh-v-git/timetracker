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
@PrepareForTest(EditActivityPageCommand.class)
public class EditActivityPageCommandTest {
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
    private EditActivityPageCommand testClass;

    private List<Category> categoryList;
    private List<Activity> activityList;
    private Activity activity;
    private Category category;

    @Before
    public void setUP() {
        category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("test");
        categoryList = new ArrayList<>();
        categoryList.add(category);
        activity = new Activity();
        activity.setActivityId(1);
        activity.setActivityName("test");
        activityList = new ArrayList<>();
        activityList.add(activity);
    }

    @Test
    public void shouldRedirectToActivityEditPageWithData() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("activityId")).thenReturn("1");
        when(activityService.getActivity(anyInt())).thenReturn(activity);
        when(categoryService.searchCategories(anyString())).thenReturn(categoryList);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(categoryService.getCategoryByID(anyInt())).thenReturn(category);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(activityService, times(1)).getActivity(anyInt());
        verify(categoryService, times(1)).searchCategories(any());
        verify(categoryService, times(1)).getCategoryByID(anyInt());
        verify(session).setAttribute("editCategory", category);
        verify(session).setAttribute("editActivity", activity);
        verify(session).setAttribute("searchCategories", categoryList);

        verify(request, never()).setAttribute(eq("error"), any());
        verify(request).setAttribute("actionStatus", "Data search done");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
    }

    @Test
    public void shouldLogActivityIdParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("activityId")).thenReturn("asad");
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "Activity ID parse error");
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
    }

    @Test
    public void shouldLogCategoryIdParseError() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("activityId")).thenReturn("1");
        when(activityService.getActivity(anyInt())).thenReturn(activity);
        when(categoryService.searchCategories(anyString())).thenReturn(categoryList);
        when(request.getParameter("categoryId")).thenReturn("asds");
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
        verify(request).getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
    }

    @Test
    public void shouldLogEmptyList() throws Exception {
        //
        // Given
        //
        whenNew(ActivityRepositorySQLImpl.class).withNoArguments().thenReturn(activityRepository);
        whenNew(ActivityService.class).withArguments(activityRepository).thenReturn(activityService);
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("activityId")).thenReturn("1");
        when(activityService.getActivity(anyInt())).thenReturn(activity);
        categoryList = new ArrayList<>();
        when(categoryService.searchCategories(anyString())).thenReturn(categoryList);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(categoryService.getCategoryByID(anyInt())).thenReturn(category);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(request).setAttribute("error", "No data");
        verify(request).setAttribute("actionStatus", "Data search failed");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_edit.jsp");
    }

}