package com.info3245.plannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.HomepageActivity;
import com.info3245.plannerapp.R;
import com.info3245.plannerapp.VerticalSpacerItemDecoration;
import com.info3245.plannerapp.adapters.ReminderItemAdapter;
import com.info3245.plannerapp.adapters.ToDoItemAdapter;
import com.info3245.plannerapp.data.ReminderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RemindersActivity extends AppCompatActivity {
    private List<ReminderItem> items;

    RecyclerView recView_reminders;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reminders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        items = List.of(
            new ReminderItem("Test 1", "gotta do smth important", LocalDateTime.now().plusDays(1)),
            new ReminderItem("Test 2", "zzzzzz", LocalDateTime.now().plusDays(2)),
            new ReminderItem("Test 3", "so unimportant", LocalDateTime.now().plusDays(7))
        );

        recView_reminders = findViewById(R.id.recView_reminders);
        recView_reminders.setLayoutManager(new LinearLayoutManager(this));
        recView_reminders.addItemDecoration(new VerticalSpacerItemDecoration(20));
        recView_reminders.setAdapter(new ReminderItemAdapter(items));
    }

    public void returnToHome (View v) {
        startActivity(new Intent(this, HomepageActivity.class));
    }
}