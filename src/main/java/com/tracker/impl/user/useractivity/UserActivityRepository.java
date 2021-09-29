package com.tracker.impl.user.useractivity;

import java.util.List;

public interface UserActivityRepository {
    List<UserActivity> findUserActivityList();

    UserActivity getUserActivity(int userActivityId);
    boolean setUserActivity(UserActivity userActivity);
    boolean updateUserActivity (UserActivity userActivity);
    boolean deleteUserActivity(int userActivityId);
}
