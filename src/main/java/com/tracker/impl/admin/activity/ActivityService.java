package com.tracker.impl.admin.activity;

import java.util.List;

public class ActivityService {
    private ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> searchActivityList(String searchName, int categoryId) {
        return activityRepository.findActivityList(searchName, categoryId);
    }

    public boolean setActivity(Activity activity) {
        return activityRepository.setActivity(activity);
    }

    public Activity getActivity(int id) {
        return activityRepository.getActivity(id);
    }

    public boolean deleteActivity(int id) {
        return activityRepository.deleteActivity(id);
    }

    public boolean updateActivity(Activity activity) {
        return activityRepository.updateActivity(activity);
    }

}
