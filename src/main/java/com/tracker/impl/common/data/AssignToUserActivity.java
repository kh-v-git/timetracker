package com.tracker.impl.common.data;

import java.time.LocalDate;

public class AssignToUserActivity {
    private int userActivityId;
    private int activityId;
    private String activityName;
    private int timeLogId;
    private String activityStatus;
    private LocalDate activityStartDate;
    private Integer activityTimeLog;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(int userActivityId) {
        this.userActivityId = userActivityId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getTimeLogId() {
        return timeLogId;
    }

    public void setTimeLogId(int timeLogId) {
        this.timeLogId = timeLogId;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
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
