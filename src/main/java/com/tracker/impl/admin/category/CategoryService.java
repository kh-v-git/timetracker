package com.tracker.impl.admin.category;

import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> searchCategories(String searchName) {
        return categoryRepository.findCategoryList(searchName);
    }
    public boolean createNewCategory(Category category) {
        return  categoryRepository.setCategory(category);
    }
    public Category getCategoryByID (int id) {
        return categoryRepository.GetCategory(id);
    }
    public boolean deleteCategoryByID (int id) {
        return categoryRepository.deleteCategory(id);
    }
    public boolean updateCategoryByID (Category category) {
        return categoryRepository.updateCategory(category);
    }

}
