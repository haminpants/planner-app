package com.info3245.plannerapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class BudgetCategoryAdapter extends RecyclerView.Adapter<BudgetCategoryAdapter.BudgetCategoryViewHolder> {
    private List<BudgetCategory> budgetCategoryList;
    private Context context;
    public BudgetCategoryAdapter(List<BudgetCategory> budgetCategoryList, Context context) {
        this.budgetCategoryList = budgetCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public BudgetCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new BudgetCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetCategoryViewHolder holder, int position) {
        BudgetCategory category = budgetCategoryList.get(position);
        holder.txtName.setText(category.getName());
        holder.txtBudgetInfo.setText(String.format("$%.2f out of $%.2f", category.getSpent(), category.getBudget()));

        //calculate the progress bar based on budget and spent
        double spent = category.getSpent();
        double budget = category.getBudget();

        int progress = (budget > 0) ? (int) ((spent / budget) * 100) : 0;
        holder.progressBar.setProgress(progress);
    }

    @Override
    public int getItemCount(){
        return budgetCategoryList.size();
    }


    public static class BudgetCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtBudgetInfo;
        ProgressBar progressBar;
        public BudgetCategoryViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCategoryName);
            txtBudgetInfo = itemView.findViewById(R.id.txtBudgetInfo);
//            txtSpent = itemView.findViewById(R.id.txtCategorySpent);
        }
    }

}
