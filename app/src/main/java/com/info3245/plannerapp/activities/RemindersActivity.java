package com.info3245.plannerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.info3245.plannerapp.data.ReminderItem;
import com.info3245.plannerapp.data.ToDoItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RemindersActivity extends AppCompatActivity {
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern("EEE, MMM d, yyyy");
    public static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern("h:mm a");
    private static final String fileName = "reminders.txt";
    private List<ReminderItem> items = new ArrayList<>();

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

        items.addAll(load());

        Intent intent = getIntent();
        String reminderTitle = intent.getStringExtra("title");
        String reminderDesc = intent.getStringExtra("description");
        String reminderDateTimeStr = intent.getStringExtra("dateTime");

        if (reminderTitle != null && reminderDesc != null && reminderDateTimeStr != null) {
            LocalDateTime time = LocalDateTime.parse(reminderDateTimeStr, DATE_TIME_FORMATTER);
            items.add(new ReminderItem(reminderTitle, reminderDesc, time));
        }

        items.sort(Comparator.comparing(ReminderItem::getTime));

        recView_reminders = findViewById(R.id.recView_reminders);
        recView_reminders.setLayoutManager(new LinearLayoutManager(this));
        recView_reminders.addItemDecoration(new VerticalSpacerItemDecoration(20));
        recView_reminders.setAdapter(new ReminderItemAdapter(items));
    }

    @Override
    public boolean onContextItemSelected (@NonNull MenuItem item) {
        ArrayList<ReminderItem> updatedItems = new ArrayList<>(items);
        updatedItems.remove(item.getItemId());
        recView_reminders.setAdapter(new ReminderItemAdapter(updatedItems));
        items = updatedItems;

        return true;
    }
    public void createNewReminder (View v) {
        save(items);
        startActivity(new Intent(this, CreateReminderActivity.class));
    }

    public void returnToHome (View v) {
        save(items);
        startActivity(new Intent(this, HomepageActivity.class));
    }

    public void save (List<ReminderItem> saveItems) {
        saveItems.removeIf(i -> i.getTime().isBefore(LocalDateTime.now()));

        try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            String reminderItems = saveItems.stream()
                .map(i -> i.getTitle() + "," + i.getDescription() + "," + i.getTime().format(DATE_TIME_FORMATTER))
                .reduce("", (current, addition) -> current + "\n" + addition).trim();
            outputStream.write(reminderItems.getBytes());
            outputStream.flush();
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
        }
    }

    public List<ReminderItem> load () {
        try (FileInputStream inputStream = openFileInput(fileName)) {
            StringBuilder output = new StringBuilder();
            int readByte;

            while ((readByte = inputStream.read()) != -1) {
                output.append((char) readByte);
            }

            if (output.toString().isBlank()) return List.of();

            return Arrays.stream(output.toString().trim().split("\n"))
                .map(s -> {
                    String[] values = s.split(",");
                    return new ReminderItem(values[0], values[1], LocalDateTime.parse(values[2], DATE_TIME_FORMATTER));
                }).collect(Collectors.toList());
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to load to do", Toast.LENGTH_SHORT).show();
        }
        return List.of();
    }
}