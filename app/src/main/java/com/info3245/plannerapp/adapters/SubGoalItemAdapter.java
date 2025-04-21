package com.info3245.plannerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.data.SubGoalItem;

import java.util.List;

public class SubGoalItemAdapter extends RecyclerView.Adapter<SubGoalItemAdapter.ViewHolder> {
    private final List<SubGoalItem> subGoalItems;

    public SubGoalItemAdapter (List<SubGoalItem> subGoalItems) {
        this.subGoalItems = subGoalItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.sub_goal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        SubGoalItem subGoalItem = subGoalItems.get(position);
        holder.cb_subGoal.setText(subGoalItem.getTitle());
        holder.cb_subGoal.setChecked(subGoalItem.isComplete());
        holder.cb_subGoal.setOnCheckedChangeListener((buttonView, isChecked) ->
            subGoalItem.setIsComplete(isChecked));
    }

    @Override
    public int getItemCount () {
        return subGoalItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox cb_subGoal;

        public ViewHolder (@NonNull View view) {
            super(view);

            cb_subGoal = view.findViewById(R.id.cb_subGoal);
        }
    }
}