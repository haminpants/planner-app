package com.info3245.plannerapp.data;

public class SubGoalItem {
    private final String title;
    private boolean isComplete;

    public SubGoalItem (String title, boolean isComplete) {
        this.title = title;
        this.isComplete = isComplete;
    }
    public SubGoalItem (String title) {
        this(title, false);
    }

    public String getTitle () {
        return title;
    }
    public boolean isComplete () {
        return isComplete;
    }
    public void setIsComplete (boolean isComplete) {
        this.isComplete = isComplete;
    }
}