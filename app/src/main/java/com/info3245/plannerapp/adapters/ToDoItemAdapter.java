package com.info3245.plannerapp.adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.data.ToDoItem;

import java.util.List;

public class ToDoItemAdapter extends RecyclerView.Adapter<ToDoItemAdapter.ViewHolder> {
    private final List<ToDoItem> items;
    private int indexCounter = 0;

    public ToDoItemAdapter (List<ToDoItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_item, parent,
            false);
        return new ViewHolder(view, indexCounter++);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        ToDoItem item = items.get(position);
        holder.txtView_label.setText(item.getTitle());
    }

    @Override
    public int getItemCount () {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final TextView txtView_label;
        private final int index;

        private ViewHolder (@NonNull View view, int index) {
            super(view);
            this.index = index;
            view.setOnCreateContextMenuListener(this);

            txtView_label = view.findViewById(R.id.txtView_title);
        }
        @Override
        public void onCreateContextMenu (ContextMenu menu, View v,
            ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, index, 0, "Delete");
        }
    }
}