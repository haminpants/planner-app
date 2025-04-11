package com.info3245.plannerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity implements BudgetDialogFragment.BudgetDialogListener {

    private EditText categoryInput;
    private ImageView btnAddExpense;
    private RecyclerView recyclerView;
    private BudgetExpenseAdapter budgetAdapter;
    private List<BudgetExpense> budgetList;

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // btnAddExpense = findViewById(R.id.btnAddExpense);
    }

    public void showDialog(View v) {
        BudgetDialogFragment dialog = new BudgetDialogFragment();
        dialog.show(getSupportFragmentManager(), "BudgetDialog");
    }

    @Override
    public void onBudgetAdded(String expenseName, double budgetLimit, double amountSpent, LocalDate date) {
        BudgetExpense expense = new BudgetExpense(expenseName, budgetLimit, amountSpent, date);
        budgetList.add(expense);
        budgetAdapter.notifyDataSetChanged();
    }
}
