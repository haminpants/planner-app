package com.info3245.plannerapp.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.data.GoalItem;
import com.info3245.plannerapp.data.SubGoalItem;
import com.info3245.plannerapp.util.VerticalSpacerItemDecoration;

import java.util.List;

public class GoalItemAdapter extends RecyclerView.Adapter<GoalItemAdapter.ViewHolder> {
    private final List<GoalItem> goalItems;
    private final Context context;

    public GoalItemAdapter (List<GoalItem> goalItems, Context context) {
        this.goalItems = goalItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.goal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        GoalItem goalItem = goalItems.get(position);
        holder.txtView_goalTitle.setText(goalItem.getTitle());
        holder.recView_subGoals.setLayoutManager(new LinearLayoutManager(context));
        holder.recView_subGoals.addItemDecoration(new VerticalSpacerItemDecoration(20));
        holder.recView_subGoals.setAdapter(goalItem.getAdapter());
        holder.btn_addSubGoal.setOnClickListener(v -> {
            View form = LayoutInflater.from(context)
                .inflate(R.layout.new_sub_goal_item, null);
            EditText editTxt_subGoalTitle = form.findViewById(R.id.editTxt_subGoalTitle);

            AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle("Add a Sub-goal")
                .setView(form)
                .setPositiveButton("Add", (dialog, which) -> {
                    goalItem.getSubGoals().add(new SubGoalItem(editTxt_subGoalTitle.getText().toString()));
                    goalItem.getAdapter().notifyItemChanged(goalItem.getSubGoals().size());
                })
                .setNegativeButton("Cancel", (dialog, which) -> {})
                .create();
            alert.show();
        });
    }

    @Override
    public int getItemCount () {
        return goalItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView txtView_goalTitle;
        private RecyclerView recView_subGoals;
        private Button btn_addSubGoal;

        public ViewHolder (@NonNull View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);

            this.txtView_goalTitle = view.findViewById(R.id.txtView_goalTitle);
            this.recView_subGoals = view.findViewById(R.id.recView_subGoals);
            this.btn_addSubGoal = view.findViewById(R.id.btn_addSubGoal);
        }

        @Override
        public void onCreateContextMenu (ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, getAdapterPosition(), 0, "Delete");
        }
    }
}