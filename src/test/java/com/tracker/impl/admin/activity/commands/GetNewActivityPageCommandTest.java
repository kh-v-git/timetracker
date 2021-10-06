package com.tracker.impl.admin.activity.commands;

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
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(GetNewActivityPageCommand.class)
public class GetNewActivityPageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryRepositorySQLImpl categoryRepository;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @InjectMocks
    private GetNewActivityPageCommand testClass;

    private List<Category> categoryList;

    @Before
    public void setUP() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("test");
        categoryList = new ArrayList<>();
        categoryList.add(category);
    }

    @Test
    public void shouldRedirectToNewActivityPage() throws Exception {
        //
        // Given
        //
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(categoryService.searchCategories(any())).thenReturn(categoryList);

        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(session).setAttribute("searchCategories", categoryList);
        verify(request, never()).setAttribute(eq("error"), any());
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_add_new.jsp");
    }

    @Test
    public void shouldLogEmptyCategoryList() throws Exception {
        //
        // Given
        //
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        categoryList = new ArrayList<>();
        when(categoryService.searchCategories(any())).thenReturn(categoryList);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(session).setAttribute(eq("searchCategories"), anyCollection().isEmpty());
        verify(request).setAttribute("error", "Wrong category request");
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/activity/activity_add_new.jsp");
    }

}