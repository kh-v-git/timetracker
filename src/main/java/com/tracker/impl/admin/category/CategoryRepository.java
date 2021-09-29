package com.tracker.impl.admin.category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findCategoryList(String searchText);
    boolean setCategory(Category category);
    Category GetCategory(int id);
    boolean deleteCategory(int id);
    Boolean updateCategory(Category category);
}

