package com.example.eventtracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextClock;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEventActivity extends AppCompatActivity {
    private EditText editEventName, editEventDetails;
    private CalendarView calendarView;
    private Button addButton, cancelButton;

    private LocalTime time;
    EventListDatabase eventListDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        initWidgets();
    }

    private void initWidgets() {
        editEventName = findViewById(R.id.name_edit_text);
        editEventDetails = findViewById(R.id.details_edit_text);
        calendarView = findViewById(R.id.calendar_edit_view);
    }
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
    public void saveEventAction(View view) {
        String eventName = editEventName.getText().toString();
        String details = editEventDetails.getText().toString();
        String date = CalendarUtils.selectedDate.toString();
        Event event = new Event(eventName, details, date);
        eventListDatabase.createEvent(event);
        finish();
    }
}