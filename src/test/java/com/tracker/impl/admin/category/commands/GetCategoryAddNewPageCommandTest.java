package com.tracker.impl.admin.category.commands;

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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(GetCategoryAddNewPageCommand.class)
public class GetCategoryAddNewPageCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @InjectMocks
    private GetCategoryAddNewPageCommand testClass;

    @Test
    public void name() throws Exception {
        //
        // Given
        //
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        //
        // When
        //
        testClass.execute(request, response);
        //
        // Then
        //
        verify(requestDispatcher).forward(request, response);
        verify(request).getRequestDispatcher("pages/admin/category/categories_add_new.jsp");

    }
}