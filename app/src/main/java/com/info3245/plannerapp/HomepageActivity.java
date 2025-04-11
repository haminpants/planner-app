package com.info3245.plannerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.info3245.plannerapp.activities.ToDoActivity;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Set up navigation buttons
        Button btnTodo = findViewById(R.id.btnTodo);
        Button btnReminders = findViewById(R.id.btnReminders);
        Button btnBudget = findViewById(R.id.btnBudget);

        btnTodo.setOnClickListener(v -> {
            startActivity(new Intent(HomepageActivity.this, ToDoActivity.class));
        });

       // btnReminders.setOnClickListener(v -> {
            // Replace with your actual Reminders activity if you have one
            //startActivity(new Intent(HomepageActivity.this, ReminderActivity.class));
       // });

        btnBudget.setOnClickListener(v -> {
            startActivity(new Intent(HomepageActivity.this, BudgetActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load the user's name
        SharedPreferences sharedPref = getSharedPreferences("PlannerAppPrefs", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "User");
        TextView greeting = findViewById(R.id.greeting);
        greeting.setText("Welcome, " + userName);

        // Load the latest reminder from a file
        String latestReminder = readLatestItemFromFile("reminders.txt");
        TextView reminderText = findViewById(R.id.reminderSummary);
        reminderText.setText("REMINDER: " + latestReminder);

        // Load the latest goal from a file
        String latestGoal = readLatestItemFromFile("goals.txt");
        TextView goalText = findViewById(R.id.goalSummary);
        goalText.setText("GOAL – " + latestGoal);
    }

    private String readLatestItemFromFile(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            fis.close();
        } catch (Exception e) {
            return "No data yet";
        }

        String[] entries = stringBuilder.toString().trim().split("\n");
        if (entries.length == 0) return "No data yet";

        return entries[entries.length - 1];
    }
}
