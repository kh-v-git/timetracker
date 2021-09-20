package com.tracker.admin.activity;

import java.util.List;

public interface ActivityRepository {
        List<Activity> findActivitiesList(String searchName, int categoryId);
        boolean setNewActivity(String addActName, String addActDescription, int categoryId);
        Activity getActivityByID (int id);
        boolean deleteActivityByID (int id);
        boolean updateActivityById (int id, String actName, String actDescription, int catId);
}
