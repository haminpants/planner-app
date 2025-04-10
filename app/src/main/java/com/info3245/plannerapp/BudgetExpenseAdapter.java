package com.info3245.plannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.List;

public class BudgetExpenseAdapter extends RecyclerView.Adapter<BudgetExpenseAdapter.BudgetExpenseViewHolder> {

    private List<BudgetExpense> expenseList;
    private Context context;

    public BudgetExpenseAdapter(List<BudgetExpense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public BudgetExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new BudgetExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetExpenseViewHolder holder, int position) {
        BudgetExpense budgetExpense = expenseList.get(position);
        holder.txtName.setText(budgetExpense.getName());
        holder.txtBudgetInfo.setText(String.format("$%.2f out of $%.2f", budgetExpense.getAmountSpent(), budgetExpense.getBudgetLimit()));

        // Calculate progress bar based on budget and spent amount
        int progress = calculateProgress(budgetExpense);
        holder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Calculate progress as percentage of budget spent
    private int calculateProgress(BudgetExpense budgetExpense) {
        BigDecimal totalBudget = budgetExpense.getBudgetLimit();
        BigDecimal spentAmount = budgetExpense.getAmountSpent();

        if (totalBudget.compareTo(BigDecimal.ZERO) > 0) {
            return spentAmount.multiply(new BigDecimal(100))
                    .divide(totalBudget, 0, BigDecimal.ROUND_DOWN).intValue();
        } else {
            return 0;
        }
    }

    public static class BudgetExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtBudgetInfo;
        ProgressBar progressBar;

        public BudgetExpenseViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCategoryName);
            txtBudgetInfo = itemView.findViewById(R.id.txtBudgetInfo);
            progressBar = itemView.findViewById(R.id.progressBarBudget);
        }
    }
}
