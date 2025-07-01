package com.example.eventtracker;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.eventtracker.CalendarUtils.daysInMonthArray;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    static String name, email, phone;
    EventListDatabase eventListDatabase;
    RecyclerView calendarRecyclerView;
    Button addEvent;
    ImageButton smsButton;
    AlertDialog alertDialog = null;
    ArrayList<Event> events;

    private ListView eventList;
    public static final String userEmail = "";
    private static final int USER_PERMISSION_REQUEST = 0;
    private static boolean smsAuth = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        addEvent = findViewById(R.id.add_event_button);
        smsButton = findViewById(R.id.sms_button);
        eventListDatabase = new EventListDatabase(this);

        Bundle bundle = getIntent().getExtras();
        // gets name, email, and phone num from bundle
        if (bundle != null) {
            name = bundle.getString("userName");
            email = bundle.getString("userEmail");
            phone = bundle.getString("userPhone");
        }

        events = (ArrayList<Event>) eventListDatabase.getAllEvents();
        // add on click listener for add event button
        addEvent.setOnClickListener(view -> {
            // Opening new AddItemActivity using intent on forgotPasswordButton click.
            Intent add = new Intent(this, AddEventActivity.class);
            add.putExtra(userEmail, email);
            startActivityForResult(add, 1);
        });
        // add on click listener for sms_button
        smsButton.setOnClickListener(view -> {
            // requests permission
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this, "Device Permission is Needed", Toast.LENGTH_LONG).show();
                }
                else {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},
                            USER_PERMISSION_REQUEST);
                }
            }
            else {
                Toast.makeText(this, "Device Permission Allowed", Toast.LENGTH_LONG).show();
            }
            alertDialog = SMSNotifications.doubleButton(this);
            alertDialog.show();
        });
    }
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.month_year_text_view);
        eventList = findViewById(R.id.eventListView);
    }

    // sets view of the month
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    // sets the selected date back one month
    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }
    // sets the selected day forward one month
    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {

        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
    public void newEventAction(View view) {
        startActivity(new Intent(this, AddEventActivity.class));
    }

    // sets sms authority to true is permission granted
    public static void AllowSendSMS() {
        smsAuth = true;
    }

    // sets sms authority to false if permission denied
    public static void DenySendSMS() {
        smsAuth = false;
    }

    // sets up sms notification to be sent
    public static void SendSMSMsg(Context context) {
        String phoneNum = phone;
        String msg = "You have an Event today";

        if (smsAuth) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, msg, null, null);
                Toast.makeText(context, "SMS sent", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
        else {
            Toast.makeText(context, "App SMS Alert Disabled", Toast.LENGTH_LONG).show();
        }
    }
}