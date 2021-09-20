package com.tracker.admin.category;

import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> searchCategories(String searchName) {
        return categoryRepository.findCategoriesList(searchName);
    }
    public boolean createNewCategory(String addCatName, String addCatDescription) {
        return  categoryRepository.setNewCategory(addCatName, addCatDescription);
    }
    public Category getCategoryByID (int id) {
        return categoryRepository.GetCategoryByID(id);
    }
    public boolean deleteCategoryByID (int id) {
        return categoryRepository.deleteCategoryByID(id);
    }
    public boolean updateCategoryByID (int id, String updateCatName, String updateCatDescription) {
        return categoryRepository.updateCategoryById(id, updateCatName, updateCatDescription);
    }

}
