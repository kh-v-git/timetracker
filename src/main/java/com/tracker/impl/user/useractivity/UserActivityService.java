package com.tracker.impl.user.useractivity;

import java.util.List;

public class UserActivityService {
    private UserActivityRepository userActivityRepository;

    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public List<UserActivity> getUserActivityList() {
        return userActivityRepository.findUserActivityList();
    }

    public UserActivity getUserActivity(int userActivityId) {
        return userActivityRepository.getUserActivity(userActivityId);
    }

    public boolean setUserActivity(UserActivity newUserActivity) {
        return userActivityRepository.setUserActivity(newUserActivity);
    }

    public boolean deleteUserActivity(int userActivityID) {
        return userActivityRepository.deleteUserActivity(userActivityID);
    }

    public boolean updateUserActivity(UserActivity updUserAct) {
        return userActivityRepository.updateUserActivity(updUserAct);
    }
}
