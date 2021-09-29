package com.tracker.impl.common.data;

import com.tracker.impl.admin.activity.Activity;

import java.util.List;

public class CategoryActivitiesList {
    private int categoryId;
    private String categoryName;
    private List<Activity> categoryActivitiesList;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Activity> getCategoryActivitiesList() {
        return categoryActivitiesList;
    }

    public void setCategoryActivitiesList(List<Activity> categoryActivitiesList) {
        this.categoryActivitiesList = categoryActivitiesList;
    }
}
