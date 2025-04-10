package com.info3245.plannerapp;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class BudgetDialogFragment extends DialogFragment {


    // Define an interface for communication
    public interface BudgetDialogListener {
        void onBudgetAdded(String categoryName, String budgetLimit, String amountSpent);
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

        // Set up the dialog's view
        builder.setView(view)
                .setMessage("Add an Expense: ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Retrieve input from the EditText fields
                        String categoryName = txtName.getText().toString();
                        String budgetLimit = txtBudgetLimit.getText().toString();
                        String amountSpent = txtAmountSpent.getText().toString();


                        // Send data to BudgetActivity using the interface
                        listener.onBudgetAdded(categoryName, budgetLimit, amountSpent);
                        // Handle the input, for example, show a toast with the inputs
                        Toast.makeText(getActivity(), "Category: " + categoryName + ", Budget: " + budgetLimit, Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog
                        Toast.makeText(getActivity(), "No Clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog
                        Toast.makeText(getActivity(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}