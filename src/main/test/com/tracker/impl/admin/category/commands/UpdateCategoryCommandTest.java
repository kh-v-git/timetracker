package com.tracker.impl.admin.category.commands;

import com.tracker.impl.admin.category.CategoryRepository;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
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

    @InjectMocks
    private UpdateCategoryCommand sut;

    @Test
    public void shouldUpdateCategory() throws Exception {
        whenNew(CategoryRepositorySQLImpl.class).withNoArguments().thenReturn(categoryRepository);
        whenNew(CategoryService.class).withArguments(categoryRepository).thenReturn(categoryService);
        when(request.getParameter("categoryId")).thenReturn("1");
        when(request.getParameter("catNewName")).thenReturn("name");
        when(request.getParameter("catNewDescription")).thenReturn("description");
        when(request.getRequestDispatcher("get_categories_main.command")).thenReturn(requestDispatcher);
        when(categoryService.updateCategoryByID(any())).thenReturn(true);

        sut.execute(request, response);

        verify(categoryService, times(1)).updateCategoryByID(any());
        verify(request, never()).setAttribute(eq("error"), any());
    }

}