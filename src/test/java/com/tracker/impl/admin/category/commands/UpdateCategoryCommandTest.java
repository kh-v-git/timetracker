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
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "jdk.internal.reflect.*"})
@PrepareForTest(UpdateCategoryCommand.class)
public class UpdateCategoryCommandTest {

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
    private UpdateCategoryCommand testClass;

    @Test
    public void shouldUpdateCategory() throws Exception {
        //
        // Given
        //
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("catNewName")).thenReturn("name");
        when(request.getParameter("catNewDescription")).thenReturn("description");
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(categoryService.updateCategoryByID(any())).thenReturn(true);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(categoryService, times(1)).updateCategoryByID(any());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(request, times(1)).getRequestDispatcher(stringCaptor.capture());
        assertEquals("get_categories_main.command", stringCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

}
