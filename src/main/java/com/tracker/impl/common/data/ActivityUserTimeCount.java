package com.tracker.impl.common.data;

public class ActivityUserTimeCount {
    private int userId;
    private int activityId;
    private String activityName;
    private int usersActivityCount;
    private int activityTimeTotalLogged;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getUsersActivityCount() {
        return usersActivityCount;
    }

    public void setUsersActivityCount(int usersActivityCount) {
        this.usersActivityCount = usersActivityCount;
    }

    public int getActivityTimeTotalLogged() {
        return activityTimeTotalLogged;
    }

    public void setActivityTimeTotalLogged(int activityTimeTotalLogged) {
        this.activityTimeTotalLogged = activityTimeTotalLogged;
    }
}
