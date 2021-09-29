package com.tracker.impl.common.data;

import java.util.List;

public interface DataRepository {
    List<AssignToUserActivity> findAllUserActivityList (int userId);
}
