package com.info3245.plannerapp.data;

import com.info3245.plannerapp.adapters.SubGoalItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoalItem {
    private final String title;
    private final List<SubGoalItem> subGoals = new ArrayList<>();
    private final SubGoalItemAdapter adapter;

    public GoalItem (String title, List<SubGoalItem> subGoals) {
        this.title = title;
        this.subGoals.addAll(subGoals);
        this.adapter = new SubGoalItemAdapter(this.subGoals);
    }
    public GoalItem (String title) {
        this(title, List.of());
    }

    public String getTitle () {
        return title;
    }
    public List<SubGoalItem> getSubGoals () {
        return subGoals;
    }
    public SubGoalItemAdapter getAdapter () {
        return adapter;
    }
}