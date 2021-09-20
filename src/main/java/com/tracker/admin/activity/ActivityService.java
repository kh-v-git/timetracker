package com.tracker.admin.activity;

import java.util.List;

public class ActivityService {
    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> searchActivities(String searchName, int categoryId) {
        return activityRepository.findActivitiesList(searchName, categoryId);
    }

    public boolean createNewActivity(String actName, String actDescription, int catID) {
        return activityRepository.setNewActivity(actName, actDescription, catID);
    }

    public Activity getActivityByID(int id) {
        return activityRepository.getActivityByID(id);
    }

    public boolean deleteActivityByID (int id) {
        return activityRepository.deleteActivityByID(id);
    }

    public boolean updateActivityById (int id, String actName, String actDescription, int catId) {
        return activityRepository.updateActivityById(id, actName,actDescription, catId);
    }

}
