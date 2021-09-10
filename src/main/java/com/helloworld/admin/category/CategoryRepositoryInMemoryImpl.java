package com.helloworld.admin.category;

import java.util.ArrayList;
import java.util.List;

//singleton
public class CategoryRepositoryInMemoryImpl implements CategoryRepository {
    //Category container
    private final List<Category> categoryContainer;

    private static final CategoryRepository categoryRepository = new CategoryRepositoryInMemoryImpl();

    private CategoryRepositoryInMemoryImpl() {

        categoryContainer = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category testCat = new Category();
            testCat.setId(i);
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
        for (Category getCat:categoryContainer) {
            if (searchName.equals(getCat.getName())) {
                returnContainer.add(getCat);
            }
        }

        return returnContainer;
    }
}
