package com.info3245.plannerapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.info3245.plannerapp.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateReminderActivity extends AppCompatActivity {
    TextView txtView_date, txtView_time, txtView_errorMsg;
    EditText editTxt_name, editTxt_description;

    LocalDateTime selectedTime = LocalDateTime.now();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_reminder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtView_date = findViewById(R.id.txtView_setReminderDate);
        txtView_time = findViewById(R.id.txtView_setReminderTime);
        txtView_errorMsg = findViewById(R.id.txtView_createReminderMsg);
        editTxt_name = findViewById(R.id.editTxt_reminderName);
        editTxt_description = findViewById(R.id.editTxt_reminderDescription);

        updateDateTimeLabels();
    }

    public void setDate (View v) {
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            selectedTime = selectedTime.withYear(year).withMonth(month + 1).withDayOfMonth(dayOfMonth);
            updateDateTimeLabels();
        }, selectedTime.getYear(), selectedTime.getMonthValue() - 1, selectedTime.getDayOfMonth());
        dialog.show();
    }

    public void setTime (View v) {
        TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            selectedTime = selectedTime.withHour(hourOfDay).withMinute(minute);
            updateDateTimeLabels();
        }, selectedTime.getHour(), selectedTime.getMinute(), false);
        dialog.show();
    }

    public void cancel (View v) {
        startActivity(new Intent(this, RemindersActivity.class));
    }

    public void create (View v) {
        if (editTxt_name.getText().toString().isBlank()) {
            txtView_errorMsg.setText(R.string.reminder_error_blank_title);
            return;
        }
        if (editTxt_description.getText().toString().isBlank()) {
            txtView_errorMsg.setText(R.string.reminder_error_blank_description);
            return;
        }
        if (selectedTime.isBefore(LocalDateTime.now())) {
            txtView_errorMsg.setText(R.string.reminder_error_past_time);
            return;
        }

        Intent intent = new Intent(this, RemindersActivity.class);
        intent.putExtra("title", editTxt_name.getText().toString());
        intent.putExtra("description", editTxt_description.getText().toString());
        intent.putExtra("dateTime", selectedTime.format(RemindersActivity.DATE_TIME_FORMATTER));
        startActivity(intent);
    }

    private void updateDateTimeLabels () {
        txtView_date.setText(selectedTime.format(RemindersActivity.DATE_FORMATTER));
        txtView_time.setText(selectedTime.format(RemindersActivity.TIME_FORMATTER));
    }
}