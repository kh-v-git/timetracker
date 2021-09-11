package com.tracker.admin.category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findCategoriesList(String searchName);
    boolean setNewCategory(String addCatName, String addCatDescription);
    Category getCategoryByID (int id);
    boolean deleteCategoryByID (int id);
    Boolean updateCategoryById (int id, String catName, String catDescription);
}

