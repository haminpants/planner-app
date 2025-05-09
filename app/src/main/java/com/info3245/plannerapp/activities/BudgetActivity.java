package com.info3245.plannerapp.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.info3245.plannerapp.fragments.BudgetDialogFragment;
import com.info3245.plannerapp.data.BudgetExpense;
import com.info3245.plannerapp.R;
import com.info3245.plannerapp.adapters.BudgetExpenseAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity implements BudgetDialogFragment.BudgetDialogListener {

    private RecyclerView recyclerView;
    private BudgetExpenseAdapter budgetAdapter;
    private List<BudgetExpense> budgetList;
    private TextView txtTotalExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);


        // Initializes RecyclerView
        recyclerView = findViewById(R.id.budgetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        budgetList = new ArrayList<>();
        budgetAdapter = new BudgetExpenseAdapter(budgetList, this);
        recyclerView.setAdapter(budgetAdapter);

//        budgetAdapter.setOnItemLongClickListener(position -> {
//            showDeleteDialog(position);
//        });
//
//        budgetAdapter.setOnItemClickListener(position -> {
//            BudgetExpense itemToEdit = budgetList.get(position);
//            showEditDialog(position, itemToEdit);
//        });

        txtTotalExpense = findViewById(R.id.txtTotalSpent);

        budgetList = new ArrayList<>();
        loadBudgetFromPrefs(); // Load saved budget list
        updateTotalTextView();

        budgetAdapter = new BudgetExpenseAdapter(budgetList, this);
        recyclerView.setAdapter(budgetAdapter);

        budgetAdapter.setOnItemLongClickListener(position -> {
            showDeleteDialog(position);
        });

        budgetAdapter.setOnItemClickListener(position -> {
            BudgetExpense itemToEdit = budgetList.get(position);
            showEditDialog(position, itemToEdit);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // btnAddExpense = findViewById(R.id.btnAddExpense);
        /*txtTotalExpense = findViewById(R.id.txtTotalSpent);*/
    }

    public void showDialog(View v) {
        BudgetDialogFragment dialog = new BudgetDialogFragment();
        dialog.show(getSupportFragmentManager(), "BudgetDialog");
    }

    public void goHomepage(View v) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    private void showDeleteDialog(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    budgetList.remove(position);
                    budgetAdapter.notifyItemRemoved(position);

                    budgetAdapter.notifyDataSetChanged();
                    saveToPrefs();

                    // Update the total TextView
                    updateTotalTextView();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditDialog(int position, BudgetExpense item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.budget_dialog_fragment, null);

        EditText txtName = view.findViewById(R.id.txtName);
        EditText txtBudgetLimit = view.findViewById(R.id.txtBudgetLimit);
        EditText txtAmountSpent = view.findViewById(R.id.txtAmountSpent);

        // Pre-fill the current values
        txtName.setText(item.getName());
        txtBudgetLimit.setText(String.valueOf(item.getBudgetLimit()));
        txtAmountSpent.setText(String.valueOf(item.getAmountSpent()));

        builder.setView(view)
                .setTitle("Edit Expense")
                .setPositiveButton("Save", (dialog, which) -> {
                    try {
                        String newName = txtName.getText().toString();
                        double newLimit = Double.parseDouble(txtBudgetLimit.getText().toString());
                        double newSpent = Double.parseDouble(txtAmountSpent.getText().toString());

                        // Update item
                        item.setName(newName);
                        item.setBudgetLimit(newLimit);
                        item.setAmountSpent(newSpent);

                        budgetAdapter.notifyItemChanged(position);
                        updateTotalTextView();
                        saveToPrefs();
                        Toast.makeText(this, "Updated successfully!", Toast.LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Invalid number format.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    @Override
    public void onBudgetAdded(String expenseName, double budgetLimit, double amountSpent, LocalDate date) {
        BudgetExpense expense = new BudgetExpense(expenseName, budgetLimit, amountSpent, date);
        budgetList.add(expense);
        budgetAdapter.notifyDataSetChanged();
        saveToPrefs();
        updateTotalTextView();
    }

    private double calculateTotalBudgetLimit() {
        double total = 0.0;
        for (BudgetExpense expense : budgetList) {
            total += expense.getBudgetLimit();
        }
        return total;
    }

    private double calculateTotalAmountSpent() {
        double total = 0.0;
        for (BudgetExpense expense : budgetList) {
            total += expense.getAmountSpent();
        }
        return total;
    }

    private void updateTotalTextView() {
        // Calculate total budget and total spent
        double totalBudget = calculateTotalBudgetLimit();
        double totalSpent = calculateTotalAmountSpent();

        // Update the TextView with total spent and total budget
        txtTotalExpense.setText(String.format("Total Spent: $%.2f / $%.2f", totalSpent, totalBudget));
    }


    private String convertBudgetListToCSV(List<BudgetExpense> budgetList) {
        StringBuilder saveData = new StringBuilder();

        for (BudgetExpense info : budgetList) {
            saveData
                    .append(info.getName()).append(",")
                    .append(info.getBudgetLimit()).append(",")
                    .append(info.getAmountSpent()).append(",")
                    .append(info.getDate().toString())
                    .append("\n");
        }

        return saveData.toString().trim(); // removes trailing newline
    }

    private void saveToPrefs() {
        SharedPreferences prefs = getSharedPreferences("budget_prefs", MODE_PRIVATE);
        prefs.edit().putString("budget_data", convertBudgetListToCSV(budgetList)).apply();  // changed here
    }

    private void loadBudgetFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("budget_prefs", MODE_PRIVATE);
        String csvData = prefs.getString("budget_data", null);  // changed here

        if (csvData != null) {
            budgetList = new ArrayList<>();
            String[] lines = csvData.split("\n");

            for (String line : lines) {
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String name = parts[0];
                    double budgetLimit = Double.parseDouble(parts[1]);
                    double amountSpent = Double.parseDouble(parts[2]);
                    LocalDate date = LocalDate.parse(parts[3]);

                    BudgetExpense expense = new BudgetExpense(name, budgetLimit, amountSpent, date);
                    budgetList.add(expense);
                }
            }
        } else {
            budgetList = new ArrayList<>();
        }

        // Refresh adapter after loading
        budgetAdapter = new BudgetExpenseAdapter(budgetList, this);
        recyclerView.setAdapter(budgetAdapter);
    }


}