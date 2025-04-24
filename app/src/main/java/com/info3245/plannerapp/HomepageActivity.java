package com.info3245.plannerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.info3245.plannerapp.activities.ToDoActivity;
import com.info3245.plannerapp.activities.RemindersActivity;
import com.info3245.plannerapp.BudgetActivity;




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

        // Set up navigation buttons
        Button btnTodo = findViewById(R.id.btnTodo);
        Button btnReminders = findViewById(R.id.btnReminders);
        Button btnBudget = findViewById(R.id.btnBudget);

        btnTodo.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, ToDoActivity.class)));

        btnReminders.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, RemindersActivity.class)));

        btnBudget.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, BudgetActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load and display user's name from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("PlannerAppPrefs", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "User");
        greetingText.setText("Welcome, " + userName);

        // Display most recent reminder and goal
        reminderText.setText("REMINDER: " + readLatestItemFromFile("reminders.txt"));
        goalText.setText("GOAL – " + readLatestItemFromFile("goals.txt"));
    }

    /**
     * Reads the last line from a local file.
     * Returns a default string if the file is empty or missing.
     */
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
