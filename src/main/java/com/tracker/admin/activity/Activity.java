package com.tracker.admin.activity;

public class Activity {
    private int activityId;
    private int activityCatId;
    private String activityName;
    private String activityDescription;

    public int getActivityCatId() {
        return activityCatId;
    }

    public void setActivityCatId(int activityCatId) {
        this.activityCatId = activityCatId;
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

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

}
