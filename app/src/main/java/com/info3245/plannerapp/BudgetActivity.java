package com.info3245.plannerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    private EditText categoryInput;
    private ImageView btnAddExpense;
    private RecyclerView budgetRecyclerView;
    private BudgetCategoryAdapter budgetAdapter;
    private List<BudgetCategory> budgetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);

        //initializes RecyclerView
        budgetRecyclerView = findViewById(R.id.budgetRecyclerView);
//        budgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // set adapter
//        budgetAdapter = new BudgetCategory();
        budgetRecyclerView.setAdapter(budgetAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAddExpense = findViewById(R.id.btnAddExpense);
//        btnAddExpense.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }
    public void showDialog(View v) {
        BudgetDialogFragment dialog = new BudgetDialogFragment();
        dialog.show(getSupportFragmentManager(), "BudgetDialog");
    }
}