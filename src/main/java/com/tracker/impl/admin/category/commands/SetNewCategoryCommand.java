package com.tracker.impl.admin.category.commands;

import com.tracker.Command;
import com.tracker.impl.admin.category.Category;
import com.tracker.impl.admin.category.CategoryRepository;
import com.tracker.impl.admin.category.CategoryRepositorySQLImpl;
import com.tracker.impl.admin.category.CategoryService;
import com.tracker.security.RequiresRole;
import com.tracker.utils.UserRolesEnum;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
@RequiresRole(UserRolesEnum.ADMIN)
public class SetNewCategoryCommand implements Command {
    private static final Logger log = LogManager.getLogger(SetNewCategoryCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CategoryRepository categoryRepository = new CategoryRepositorySQLImpl();
        CategoryService categoryService = new CategoryService(categoryRepository);

        String addCatName = Optional.ofNullable(request.getParameter("catNewName"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");
        String addCatDescription = Optional.ofNullable(request.getParameter("catNewDescription"))
                .map(Object::toString)
                .map(String::trim)
                .orElse("");

        if (addCatName.isEmpty() || addCatDescription.isEmpty()) {
            request.setAttribute("error", "Category add error");
            log.log(Level.ERROR, "Category add error");
        } else {
            Category newCategory = new Category();
            newCategory.setCategoryName(addCatName);
            newCategory.setCategoryDescription(addCatDescription);

            categoryService.createNewCategory(newCategory);
            request.setAttribute("actionStatus", "Set new category success.");
        }

        request.getRequestDispatcher("get_categories_main.command").forward(request, response);
    }
}
