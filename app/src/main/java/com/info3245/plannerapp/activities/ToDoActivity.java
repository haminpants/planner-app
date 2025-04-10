package com.info3245.plannerapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import com.info3245.plannerapp.HomepageActivity;
import com.info3245.plannerapp.R;
import com.info3245.plannerapp.VerticalSpacerItemDecoration;
import com.info3245.plannerapp.data.ToDoItem;
import com.info3245.plannerapp.adapters.ToDoItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {
    public List<ToDoItem> items = new ArrayList<>();

    TextView txtView_welcome;
    RecyclerView recView_toDo;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtView_welcome = findViewById(R.id.txtView_welcome);

        recView_toDo = findViewById(R.id.recView_toDo);
        recView_toDo.setLayoutManager(new LinearLayoutManager(this));
        recView_toDo.addItemDecoration(new VerticalSpacerItemDecoration(20));
        recView_toDo.setAdapter(new ToDoItemAdapter(items));
    }

    public void returnToHome (View v) {
        startActivity(new Intent(this, HomepageActivity.class));
    }

    public void createNewItem (View v) {
        View form = getLayoutInflater().inflate(R.layout.new_to_do_item, null);

        AlertDialog alertDialogue = new AlertDialog.Builder(this).setTitle("Create a To Do Item")
            .setView(form)
            .setPositiveButton("Add", ((dialog, which) -> addToDoItem(((EditText) form.findViewById(
                R.id.editTxt_title)).getText().toString())))
            .setNegativeButton("Cancel", ((dialog, which) -> {}))
            .create();
        alertDialogue.show();
    }

    @Override
    public boolean onContextItemSelected (@NonNull MenuItem item) {
        removeToDoItem(item.getItemId());
        return true;
    }

    private void addToDoItem (String title) {
        ArrayList<ToDoItem> updatedList = new ArrayList<>(items);
        updatedList.add(new ToDoItem(title));
        recView_toDo.setAdapter(new ToDoItemAdapter(updatedList));
        items = updatedList;
    }

    private void removeToDoItem (int index) {
        ArrayList<ToDoItem> updatedList = new ArrayList<>(items);
        updatedList.remove(index);
        recView_toDo.setAdapter(new ToDoItemAdapter(updatedList));
        items = updatedList;
    }
}