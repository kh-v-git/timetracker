package com.tracker.impl.admin.category.commands;

import com.tracker.impl.admin.category.Category;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(EditCategoryPageCommand.class)
public class EditCategoryPageCommandTest {
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

    @Mock
    private HttpSession session;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @InjectMocks
    private EditCategoryPageCommand testClass;

    @Test
    public void shouldRedirectToEditCategoryPage() throws Exception {
        Category category = new Category();

        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(categoryService.getCategoryByID(anyInt())).thenReturn(category);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        testClass.execute(request, response);

        verify(categoryService, times(1)).getCategoryByID(anyInt());
        verify(request, never()).setAttribute(eq("error"), any());
        verify(session).setAttribute("editCategory", category);
        //verify(session).setAttribute(eq("editCategory"), any());
        verify(request, times(1)).getRequestDispatcher(stringCaptor.capture());
        assertEquals("pages/admin/category/category_edit.jsp", stringCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void shouldCatchNumberFormatException() throws Exception {
        Category category = new Category();

        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("categoryId")).thenReturn("ljk");

        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);

        testClass.execute(request, response);

        verify(request).setAttribute("error", "Category add error");
        verify(request, times(1)).getRequestDispatcher(stringCaptor.capture());
        assertEquals("pages/admin/category/category_edit.jsp", stringCaptor.getValue());
        verify(requestDispatcher).forward(request, response);
    }

}