package com.info3245.plannerapp;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class BudgetDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the layout for the dialog
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.budget_dialog_fragment, null);

        // Get references to the EditText fields
        EditText newCategoryName = view.findViewById(R.id.newCategoryName);
        EditText editTextNumberDecimal = view.findViewById(R.id.newCategoryBudget);

        // Set up the dialog's view
        builder.setView(view)
                .setMessage("Do you really want to add this category?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Retrieve input from the EditText fields
                        String categoryName = newCategoryName.getText().toString();
                        String budgetAmount = editTextNumberDecimal.getText().toString();

                        // Handle the input, for example, show a toast with the inputs
                        Toast.makeText(getActivity(), "Category: " + categoryName + ", Budget: " + budgetAmount, Toast.LENGTH_SHORT).show();
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