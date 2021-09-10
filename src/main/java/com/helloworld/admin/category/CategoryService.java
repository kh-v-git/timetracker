package com.helloworld.admin.category;

import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoriesByName(String searchName) {
        return categoryRepository.findCategoriesList(searchName);
    }

}
