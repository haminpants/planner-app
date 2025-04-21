package com.info3245.plannerapp.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.adapters.GoalItemAdapter;
import com.info3245.plannerapp.data.GoalItem;
import com.info3245.plannerapp.data.ToDoItem;
import com.info3245.plannerapp.util.VerticalSpacerItemDecoration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GoalActivity extends AppCompatActivity {
    private static final String fileName = "finance_goal.txt";

    TextView txtView_financeGoalHeader;
    TextView txtView_financeGoalDescription;
    TextView txtView_financeGoalProgress;
    ProgressBar progBar_financeGoal;
    Button btn_updateFinanceGoalProgress;
    Button btn_financeGoalData;
    RecyclerView recView_goals;

    private int financeGoal = 0;
    private String financeGoalDescription = "";
    private int financeGoalProgress = 0;

    private GoalItemAdapter goalItemAdapter;
    private List<GoalItem> goalItems = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtView_financeGoalHeader = findViewById(R.id.txtView_financeGoalHeader);
        txtView_financeGoalDescription = findViewById(R.id.txtView_financeGoalDescription);
        txtView_financeGoalProgress = findViewById(R.id.txtView_financeGoalProgress);
        progBar_financeGoal = findViewById(R.id.progBar_financeGoal);
        btn_updateFinanceGoalProgress = findViewById(R.id.btn_updateFinanceGoalProgress);
        btn_financeGoalData = findViewById(R.id.btn_financeGoalData);
        recView_goals = findViewById(R.id.recView_goals);

        loadFinanceGoalData();
        updateFinanceGoalDisplay();

        goalItems.addAll(List.of(
            new GoalItem("Be cool"),
            new GoalItem("Don't die")
        ));

        goalItemAdapter = new GoalItemAdapter(goalItems);
        recView_goals.setLayoutManager(new LinearLayoutManager(this));
        recView_goals.addItemDecoration(new VerticalSpacerItemDecoration(20));
        recView_goals.setAdapter(goalItemAdapter);
    }

    @Override
    public boolean onContextItemSelected (@NonNull MenuItem item) {
        goalItems.remove(item.getItemId());
        goalItemAdapter.notifyItemRemoved(item.getItemId());
        return true;
    }

    private void saveData () {
        try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            String data = financeGoalProgress + "," + financeGoal + "\n" + financeGoalDescription;
            outputStream.write(data.getBytes());
            outputStream.flush();
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to save finance goals", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadFinanceGoalData () {
        try (FileInputStream inputStream = openFileInput(fileName)) {
            StringBuilder output = new StringBuilder();
            int readByte;

            while ((readByte = inputStream.read()) != -1) {
                output.append((char) readByte);
            }
            if (output.toString().isBlank()) return;

            String[] dataStr = output.toString().split("\n", 2);
            financeGoalProgress = Integer.parseInt(dataStr[0].split(",")[0]);
            financeGoal = Integer.parseInt(dataStr[0].split(",")[1]);
            financeGoalDescription = dataStr[1];
        }
        catch (FileNotFoundException ignored) {}
        catch (Exception e) {
            Toast.makeText(this, "Failed to load finance data", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFinanceGoalDisplay () {
        if (financeGoal != 0) {
            txtView_financeGoalHeader.setText("Save " + financeGoal);
            txtView_financeGoalDescription.setText(financeGoalDescription);
            progBar_financeGoal.setMax(financeGoal);
            progBar_financeGoal.setProgress(financeGoalProgress);
            txtView_financeGoalProgress.setText(String.format("%.0f%%",
                (((financeGoalProgress + 0.0) / financeGoal)) * 100));
            btn_financeGoalData.setText(R.string.update_finance_goal);
        }
        else {
            txtView_financeGoalHeader.setText(R.string.no_finance_goal_set);
            btn_financeGoalData.setText(R.string.setup_finance_goal);
        }

        int visibility = financeGoal != 0 ? VISIBLE : GONE;
        txtView_financeGoalDescription.setVisibility(visibility);
        txtView_financeGoalProgress.setVisibility(visibility);
        btn_updateFinanceGoalProgress.setVisibility(visibility);
        progBar_financeGoal.setVisibility(visibility);
    }

    public void loadHomepage (View v) {
        saveData();
        startActivity(new Intent(this, HomepageActivity.class));
    }

    public void setFinanceGoalData (View v) {
        View form = getLayoutInflater().inflate(R.layout.set_finance_goal_data, null);
        EditText editTxt_amount = form.findViewById(R.id.editTxt_financeGoalAmount);
        EditText editTxt_description = form.findViewById(R.id.editTxt_financeGoalDescription);

        if (financeGoal != 0) {
            editTxt_amount.setText(String.valueOf(financeGoal));
            editTxt_description.setText(financeGoalDescription);
        }

        AlertDialog alert = new AlertDialog.Builder(this)
            .setTitle("Set Finance Goal Data")
            .setView(form)
            .setPositiveButton("Save", (dialog, which) -> {
                int amount = Integer.parseInt(editTxt_amount.getText().toString());
                if (amount > 0) {
                    financeGoal = amount;
                    financeGoalDescription = editTxt_description.getText().toString();
                    updateFinanceGoalDisplay();
                    saveData();
                }
                else {
                    Toast.makeText(this, "Finance goal amount must be greater than 0",
                        Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("Cancel", ((dialog, which) -> {}))
            .create();

        alert.show();
    }

    public void setFinanceGoalProgress (View v) {
        View form = getLayoutInflater().inflate(R.layout.update_finance_goal_progress, null);
        EditText editTxt_progress = form.findViewById(R.id.editTxt_financeGoalProgress);

        if (financeGoalProgress != 0) {
            editTxt_progress.setText(String.valueOf(financeGoalProgress));
        }

        AlertDialog alert = new AlertDialog.Builder(this)
            .setTitle("Update Goal Progress")
            .setView(form)
            .setPositiveButton("Update", (dialog, which) -> {
                int progress = Integer.parseInt(editTxt_progress.getText().toString());
                if (progress > 0) {
                    financeGoalProgress = progress;
                    updateFinanceGoalDisplay();
                    saveData();
                }
                else {
                    Toast.makeText(this, "Progress must be greater than 0",
                        Toast.LENGTH_LONG).show();
                }
            }).setNegativeButton("Cancel", (dialog, which) -> {})
            .create();

        alert.show();
    }

    public void createNewGoal (View v) {
        View form = getLayoutInflater().inflate(R.layout.new_goal_item, null);
        EditText editTxt_goalTitle = form.findViewById(R.id.editTxt_goalTitle);

        AlertDialog alert = new AlertDialog.Builder(this)
            .setTitle("Create A New Goal")
            .setView(form)
            .setPositiveButton("Create", (dialog, which) -> {
                goalItems.add(new GoalItem(editTxt_goalTitle.getText().toString()));
                goalItemAdapter.notifyItemChanged(goalItems.size());
            })
            .setNegativeButton("Cancel", (dialog, which) -> {})
            .create();
        alert.show();
    }
}