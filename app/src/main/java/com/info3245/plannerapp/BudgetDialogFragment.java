package com.info3245.plannerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class BudgetDialogFragment extends DialogFragment {

    // Define an interface for communication with double values
    public interface BudgetDialogListener {
        void onBudgetAdded(String categoryName, double budgetLimit, double amountSpent);
    }

    private BudgetDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Ensure the activity implements the interface
            listener = (BudgetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BudgetDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.budget_dialog_fragment, null);

        // Get references to the EditText fields
        EditText txtName = view.findViewById(R.id.txtName);
        EditText txtBudgetLimit = view.findViewById(R.id.txtBudgetLimit);
        EditText txtAmountSpent = view.findViewById(R.id.txtAmountSpent);

        builder.setView(view)
                .setMessage("Add an Expense: ")
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Retrieve input from the EditText fields
                    String expenseName = txtName.getText().toString();
                    String budgetLimitStr = txtBudgetLimit.getText().toString();
                    String amountSpentStr = txtAmountSpent.getText().toString();

                    try {
                        double budgetLimit = Double.parseDouble(budgetLimitStr);
                        double amountSpent = Double.parseDouble(amountSpentStr);

                        // Send data to BudgetActivity using the interface
                        listener.onBudgetAdded(expenseName, budgetLimit, amountSpent);

                        Toast.makeText(getActivity(),
                                "Category: " + expenseName + ", Budget: $" + budgetLimit,
                                Toast.LENGTH_SHORT).show();

                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(),
                                "Please enter valid numbers for budget and amount spent.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", (dialog, id) -> {
                    Toast.makeText(getActivity(), "No Clicked", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Cancel", (dialog, id) -> {
                    Toast.makeText(getActivity(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                });

        return builder.create();
    }
}
