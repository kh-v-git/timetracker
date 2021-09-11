package com.tracker.admin.category;

import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoriesByName(String searchName) {
        return categoryRepository.findCategoriesList(searchName);
    }
    public boolean setNewCategoryByName(String addCatName, String addCatDescription) {
        return  categoryRepository.setNewCategory(addCatName, addCatDescription);
    }
    public Category getCategoryByID (int id) {
        return categoryRepository.getCategoryByID(id);
    }
    public boolean deleteCategoryByID (int id) {
        return categoryRepository.deleteCategoryByID(id);
    }
    public boolean updateCategoryByID (int id, String updateCatName, String updateCatDescription) {
        return categoryRepository.updateCategoryById(id, updateCatName, updateCatDescription);
    }

}
