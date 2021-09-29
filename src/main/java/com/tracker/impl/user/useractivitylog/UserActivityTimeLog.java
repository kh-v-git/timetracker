package com.tracker.impl.user.useractivitylog;


import java.time.LocalDate;

public class UserActivityTimeLog {
    private int timeLogId;
    private int userActivityId;
    private LocalDate activityStartDate;
    private int activityTimeLog;

    public int getTimeLogId() {
        return timeLogId;
    }

    public void setTimeLogId(int timeLogId) {
        this.timeLogId = timeLogId;
    }

    public int getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(int userActivityId) {
        this.userActivityId = userActivityId;
    }

    public LocalDate getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(LocalDate activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public int getActivityTimeLog() {
        return activityTimeLog;
    }

    public void setActivityTimeLog(int activityTimeLog) {
        this.activityTimeLog = activityTimeLog;
    }
}
