package com.info3245.plannerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.info3245.plannerapp.activities.ToDoActivity;
import com.info3245.plannerapp.data.BudgetActivity;
import com.info3245.plannerapp.activities.RemindersActivity;
import com.info3245.plannerapp.activities.GoalActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HomepageActivity extends AppCompatActivity {

    private TextView greetingText;
    private TextView reminderText;
    private TextView goalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Initialize views
        greetingText = findViewById(R.id.greeting);
        reminderText = findViewById(R.id.reminderSummary);
        goalText = findViewById(R.id.goalSummary);

        // Set up buttons
        Button btnTodo = findViewById(R.id.btnTodo);
        Button btnReminders = findViewById(R.id.btnReminders);
        Button btnBudget = findViewById(R.id.btnBudget);
        Button btnGoal = findViewById(R.id.btnGoal);

        btnTodo.setOnClickListener(v -> startActivity(new Intent(this, ToDoActivity.class)));
        btnReminders.setOnClickListener(v -> startActivity(new Intent(this, RemindersActivity.class)));
        btnBudget.setOnClickListener(v -> startActivity(new Intent(this, BudgetActivity.class)));
        btnGoal.setOnClickListener(v -> startActivity(new Intent(this, GoalActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getSharedPreferences("PlannerAppPrefs", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "User");
        greetingText.setText("Welcome, " + userName);

        reminderText.setText("REMINDER: " + readLatestItemFromFile("reminders.txt"));
        goalText.setText("GOAL – " + readLatestItemFromFile("goals.txt"));
    }

    private String readLatestItemFromFile(String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = openFileInput(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

        } catch (Exception e) {
            return "No data yet";
        }

        String[] entries = stringBuilder.toString().trim().split("\n");
        return entries.length > 0 ? entries[entries.length - 1] : "No data yet";
    }
}
