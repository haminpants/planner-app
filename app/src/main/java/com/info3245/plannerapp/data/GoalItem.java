package com.info3245.plannerapp.data;

import java.util.ArrayList;
import java.util.List;

public class GoalItem {
    private final String title;
    private final List<String> subGoals = new ArrayList<>();

    public GoalItem (String title, List<String> subGoals) {
        this.title = title;
        this.subGoals.addAll(subGoals);
    }
    public GoalItem (String title) {
        this(title, List.of());
    }

    public String getTitle () {
        return title;
    }
    public List<String> getSubGoals () {
        return subGoals;
    }
}