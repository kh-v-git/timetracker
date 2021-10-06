package com.tracker.impl.admin.category.commands;

import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
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
@PowerMockIgnore("javax.management.*")
@PrepareForTest(GetCategoriesMainPageCommand.class)
public class GetCategoriesMainPageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private CategoryService categoryService;
    @Mock
    private CategoryRepositorySQLImpl categoryRepository;

    @Mock
    private HttpSession session;


    @InjectMocks
    private GetCategoriesMainPageCommand testClass;

    @Test
    public void shouldRedirectToCatMainPage() throws Exception {
        //
        // Given
        //
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("test");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchText")).thenReturn("hey");
        when(categoryService.searchCategories(anyString())).thenReturn(categoryList);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(session).setAttribute("searchCategories", categoryList);
        verify(session).setAttribute("searchText", "hey");
        verify(categoryService, times(1)).searchCategories(any());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/category/categories.jsp");
    }

    @Test
    public void shouldSetErrorAttributeMainPage() throws Exception {
        //
        // Given
        //
        List<Category> categoryList = new ArrayList<>();
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("searchText")).thenReturn("hey");
        when(categoryService.searchCategories(anyString())).thenReturn(categoryList);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(session).setAttribute("searchCategories", categoryList);
        verify(session).setAttribute("searchText", "hey");
        verify(categoryService, times(1)).searchCategories(any());
        verify(request).setAttribute("error", "No categories found");
        verify(requestDispatcher).forward(request, response);
    }
}