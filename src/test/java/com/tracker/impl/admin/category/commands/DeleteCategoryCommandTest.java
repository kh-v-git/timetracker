package com.tracker.impl.admin.category.commands;

import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(DeleteCategoryCommand.class)
public class DeleteCategoryCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CategoryService categoryService;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private CategoryRepositorySQLImpl categoryRepository;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @InjectMocks
    private DeleteCategoryCommand testClass;

    @Test
    public void shouldDeleteCategory() throws Exception {
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(categoryService.deleteCategoryByID(anyInt())).thenReturn(true);

        testClass.execute(request, response);

        verify(categoryService, times(1)).deleteCategoryByID(anyInt());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request, times(1)).getRequestDispatcher(stringCaptor.capture());
        assertEquals("get_categories_main.command", stringCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldSetErrorAttributeCategory() throws Exception {
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);

        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getRequestDispatcher("get_categories_main.command")).thenReturn(requestDispatcher);
        when(categoryService.deleteCategoryByID(anyInt())).thenReturn(false);

        testClass.execute(request, response);

        verify(categoryService, times(1)).deleteCategoryByID(anyInt());
        verify(request).setAttribute("error", "Category delete error");
        verify(requestDispatcher).forward(request, response);
    }
}
