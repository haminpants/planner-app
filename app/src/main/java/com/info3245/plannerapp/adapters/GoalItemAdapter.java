package com.info3245.plannerapp.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.data.GoalItem;

import java.util.List;

public class GoalItemAdapter extends RecyclerView.Adapter<GoalItemAdapter.ViewHolder> {
    private final List<GoalItem> goalItems;

    public GoalItemAdapter (List<GoalItem> goalItems) {
        this.goalItems = goalItems;
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
        // TODO: setup goals recycler view
    }

    @Override
    public int getItemCount () {
        return goalItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView txtView_goalTitle;
        private RecyclerView recView_subGoals;

        public ViewHolder (@NonNull View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);

            this.txtView_goalTitle = view.findViewById(R.id.txtView_goalTitle);
            this.recView_subGoals = view.findViewById(R.id.recView_subGoals);
        }

        @Override
        public void onCreateContextMenu (ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, getAdapterPosition(), 0, "Delete");
        }
    }
}