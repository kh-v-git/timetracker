package com.helloworld.admin.category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findCategoriesList(String searchName);
}

