package com.info3245.plannerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.data.ReminderItem;

import java.util.List;

public class ReminderItemAdapter extends RecyclerView.Adapter<ReminderItemAdapter.ViewHolder> {
    private final List<ReminderItem> items;
    private int indexCounter = 0;

    public ReminderItemAdapter (List<ReminderItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(view, indexCounter++);
    }
    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        ReminderItem item = items.get(position);
        holder.txtView_title.setText(item.getTitle());
        holder.txtView_description.setText(item.getDescription());
        holder.txtView_time.setText(item.getFormattedTime());
    }
    @Override
    public int getItemCount () {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtView_title;
        private TextView txtView_description;
        private TextView txtView_time;
        private ImageButton imgBtn_priority;
        private final int index;

        public ViewHolder (@NonNull View view, int indexCounter) {
            super(view);
            this.index = indexCounter++;

            this.txtView_title = view.findViewById(R.id.txtView_reminderTitle);
            this.txtView_description = view.findViewById(R.id.txtView_reminderDescription);
            this.txtView_time = view.findViewById(R.id.txtView_reminderTime);
            this.imgBtn_priority = view.findViewById(R.id.imgBtn_reminderPriority);
        }
    }
}