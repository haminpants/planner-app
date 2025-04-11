package com.info3245.plannerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.info3245.plannerapp.R;
import com.info3245.plannerapp.util.VerticalSpacerItemDecoration;
import com.info3245.plannerapp.adapters.ToDoItemAdapter;
import com.info3245.plannerapp.data.ToDoItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoActivity extends AppCompatActivity {
    private static final String fileName = "todo_data.txt";
    public List<ToDoItem> items;

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

        items = loadToDo();

        txtView_welcome = findViewById(R.id.txtView_welcome);

        recView_toDo = findViewById(R.id.recView_toDo);
        recView_toDo.setLayoutManager(new LinearLayoutManager(this));
        recView_toDo.addItemDecoration(new VerticalSpacerItemDecoration(20));
        recView_toDo.setAdapter(new ToDoItemAdapter(items));
    }

    public void returnToHome (View v) {
        List<Integer> completedIndexes = getCompletedItemIndexes();
        ArrayList<ToDoItem> saveItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            if (completedIndexes.contains(i)) continue;
            saveItems.add(items.get(i));
        }

        saveToDo(saveItems);
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

    private void saveToDo (List<ToDoItem> saveItems) {
        try (FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            String toDoItems = saveItems.stream().map(ToDoItem::getTitle).reduce("",
                (current, addition) -> current + "\n" + addition).trim();
            outputStream.write(toDoItems.getBytes());
            outputStream.flush();
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ToDoItem> loadToDo () {
        try (FileInputStream inputStream = openFileInput(fileName)) {
            StringBuilder output = new StringBuilder();
            int readByte;

            while ((readByte = inputStream.read()) != -1) {
                output.append((char) readByte);
            }

            if (output.toString().isBlank()) return List.of();

            return Arrays.stream(output.toString().trim().split("\n"))
                .map(ToDoItem::new).collect(Collectors.toList());
        }
        catch (Exception e) {
            Toast.makeText(this, "Failed to load to do", Toast.LENGTH_SHORT).show();
        }
        return List.of();
    }

    private List<Integer> getCompletedItemIndexes () {
        ArrayList<Integer> removeQueue = new ArrayList<>();
        for (int i = 0; i < recView_toDo.getChildCount(); i++) {
            ToDoItemAdapter.ViewHolder item =
                (ToDoItemAdapter.ViewHolder) recView_toDo.findViewHolderForAdapterPosition(i);
            if (item != null && item.isComplete()) removeQueue.add(i);
        }
        return removeQueue;
    }
}