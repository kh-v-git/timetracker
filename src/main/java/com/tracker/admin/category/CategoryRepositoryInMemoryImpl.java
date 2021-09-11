package com.tracker.admin.category;

import java.util.ArrayList;
import java.util.List;

//singleton
public class CategoryRepositoryInMemoryImpl implements CategoryRepository {
    //Category container
    private final List<Category> categoryContainer;
    private int id = 0;
    private static final CategoryRepository categoryRepository = new CategoryRepositoryInMemoryImpl();

    private CategoryRepositoryInMemoryImpl() {

        categoryContainer = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Category testCat = new Category();
            testCat.setId(id);
            id++;
            testCat.setName("Swim " + i);
            testCat.setDescription("Swim " + i + " Swim");
            categoryContainer.add(testCat);
        }
    }

    public static CategoryRepository getInstance() {
        return categoryRepository;
    }

    @Override
    public List<Category> findCategoriesList(String searchName) {
        if (searchName.isEmpty()) {
            return new ArrayList<>(categoryContainer);
        }
        List<Category> returnContainer = new ArrayList<>();
        for (Category getCat : categoryContainer) {
            if (searchName.equals(getCat.getName())) {
                returnContainer.add(getCat);
            }
        }
        return returnContainer;
    }

    @Override
    public boolean setNewCategory(String addCatName, String addCatDescription) {
        Category testCat = new Category();
        testCat.setName(addCatName);
        testCat.setDescription(addCatDescription);
        testCat.setId(id++);
        categoryContainer.add(testCat);
        return true;
    }

    @Override
    public Category getCategoryByID(int id) {
        Category testCat = new Category();
        for (Category category : categoryContainer) {
            if (category.getId() == id) {
                testCat.setId(category.getId());
                testCat.setName(category.getName());
                testCat.setDescription(category.getDescription());
                return testCat;
            }
        }
        return testCat;
    }

    @Override
    public boolean deleteCategoryByID(int id) {
        for (Category category : categoryContainer) {
            if (category.getId() == id) {
                categoryContainer.remove(category);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateCategoryById(int id, String catName, String catDescription) {
        for (Category category : categoryContainer) {
            if (category.getId() == id) {
                category.setName(catName);
                category.setDescription(catDescription);
                return true;
            }
        }
        return false;
    }
}
