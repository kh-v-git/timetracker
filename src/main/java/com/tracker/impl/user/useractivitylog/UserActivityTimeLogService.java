package com.tracker.impl.user.useractivitylog;


import java.util.List;

public class UserActivityTimeLogService {
    private UserActivityTimeLogRepository userActivityTimeLogRepository;

    public UserActivityTimeLogService(UserActivityTimeLogRepository userActivityTimeLogRepository) {
        this.userActivityTimeLogRepository = userActivityTimeLogRepository;
    }

    public List<UserActivityTimeLog> getUserActivityTimeLogList() {
        return userActivityTimeLogRepository.findUserActivityTimeLogList();
    }

    public boolean setUserActivityTimeLog(UserActivityTimeLog newLog) {
        return userActivityTimeLogRepository.setUserActivityTimeLog(newLog);
    }

    public UserActivityTimeLog getUserActivityTimeLog(int logID) {
        return userActivityTimeLogRepository.getUserActivityTimeLog(logID);
    }

    public boolean deleteUserActivityTimeLog(int logID) {
        return userActivityTimeLogRepository.deleteUserActivityTimeLog(logID);
    }

    public boolean updateUserActivityTimeLOg(UserActivityTimeLog updLog) {
        return userActivityTimeLogRepository.updateUserActivityTimeLog(updLog);
    }

}
