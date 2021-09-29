package com.tracker.impl.admin.category.commands;

import com.tracker.Command;
import com.tracker.impl.admin.category.*;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiresRole(UserRolesEnum.ADMIN)
public class EditCategoryPageCommand implements Command {
    private static final Logger log = LogManager.getLogger(EditCategoryPageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository =  new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        int editCatID;
        try {
        editCatID = Integer.parseInt(request.getParameter("categoryId"));
        Category editCat = categoryService.getCategoryByID(editCatID);
        request.getSession().setAttribute("editCategory", editCat);
        }catch (NumberFormatException n) {
            request.setAttribute("error", "Category add error");
            log.log(Level.ERROR, "Category ID parse error. No Id found.", n);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("pages/admin/category/category_edit.jsp");
        requestDispatcher.forward(request, response);
    }
}
