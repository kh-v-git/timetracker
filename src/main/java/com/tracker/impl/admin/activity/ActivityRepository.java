package com.tracker.impl.admin.activity;

import java.util.List;

public interface ActivityRepository {
        List<Activity> findActivityList(String searchText, int categoryId);
        boolean setActivity(Activity activity);
        Activity getActivity(int id);
        boolean deleteActivity(int id);
        boolean updateActivity(Activity activity);
}
