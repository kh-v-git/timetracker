package com.tracker.impl.user.useractivitylog;

import java.util.List;

public interface UserActivityTimeLogRepository {
    List<UserActivityTimeLog> findUserActivityTimeLogList();

    UserActivityTimeLog getUserActivityTimeLog (int userActivityTimeLogId);
    boolean setUserActivityTimeLog (UserActivityTimeLog userActivityTimeLog);
    boolean updateUserActivityTimeLog (UserActivityTimeLog userActivityTimeLog);
    boolean deleteUserActivityTimeLog (int userActivityTimeLogId);
}
