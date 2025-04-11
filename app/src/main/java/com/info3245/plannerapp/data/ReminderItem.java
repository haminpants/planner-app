package com.info3245.plannerapp.data;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ReminderItem {
    private static final DateTimeFormatter format =
        DateTimeFormatter.ofPattern("EEE, LLLL d @ h:m a");

    private final String title;
    private final String description;
    private final LocalDateTime time;

    public ReminderItem (String title, String description, LocalDateTime time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTitle () {
        return title;
    }
    public String getDescription () {
        return description;
    }
    public LocalDateTime getTime () {
        return time;
    }
    public String getFormattedTime () {
        return time.format(format);
    }
}