package com.info3245.plannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        //deleting
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(position);
            }
            return true;
        });

        // editing
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Calculate progress as percentage of budget spent using double
    private int calculateProgress(BudgetExpense budgetExpense) {
        double totalBudget = budgetExpense.getBudgetLimit();
        double spentAmount = budgetExpense.getAmountSpent();

        if (totalBudget > 0) {
            return (int) Math.floor((spentAmount / totalBudget) * 100);
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

    // related to deleting an expense
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    // related to editing an expense
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener clickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
}
