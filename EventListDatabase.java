package com.example.eventtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventListDatabase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "eventList.db";

    public EventListDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    private static final class EventTable {
        private static final String TABLE = "Events";
        private static final String ID = "_id";
        private static final String EVENT_NAME = "title";
        private static final String EVENT_DETAILS = "details";
        private static final String USER_EMAIL = "email";
        private static final String EVENT_DATE = "date";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EventTable.TABLE + " (" +
                EventTable.ID + " integer primary key autoincrement, " +
                EventTable.EVENT_NAME + " text, " +
                EventTable.EVENT_DETAILS + " text, " +
                EventTable.USER_EMAIL + " text, " +
                EventTable.EVENT_DATE + " date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + EventTable.TABLE);
        onCreate(db);
    }
    // CRUD for Event Database (create, read, update, delete)
    // Create
    public void createEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventTable.EVENT_NAME, event.getTitle());
        values.put(EventTable.USER_EMAIL, event.getUser_email());
        values.put(EventTable.EVENT_DETAILS, event.getDetails());
        values.put(EventTable.EVENT_DATE, event.getDate());

        db.insert(EventTable.TABLE, null, values);
        db.close();
    }

    // Read
    public Event readEvent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EventTable.TABLE,
                new String[] { EventTable.ID, EventTable.EVENT_NAME,
                        EventTable.EVENT_DETAILS, EventTable.EVENT_DATE}, EventTable.ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Event event = new Event(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();
        return event;
    }

    public Event readEventDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EventTable.TABLE,
                new String[] { EventTable.ID, EventTable.EVENT_NAME, EventTable.EVENT_DETAILS,
                        EventTable.EVENT_DATE}, EventTable.EVENT_DATE + " = ?",
                new String[] {String.valueOf(date)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Event event = new Event(Integer.parseInt(Objects.requireNonNull(cursor).getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();
        return event;
    }

    // update
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(EventTable.EVENT_NAME, event.getTitle());
        values.put(EventTable.EVENT_DETAILS, event.getDetails());
        values.put(EventTable.EVENT_DATE, event.getDate());

        return db.update(EventTable.TABLE, values, EventTable.ID + " ?", new String[] {String.valueOf(event.getId())});
    }

    // delete
    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(EventTable.TABLE, EventTable.ID + " = ?", new String[] {String.valueOf(event.getId())});
        db.close();
    }

    // function for getting all items in database
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();

        String searchQuery = "SELECT * FROM " + EventTable.TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setTitle(cursor.getString(1));
                event.setUser_email(cursor.getString(2));
                event.setDetails(cursor.getString(3));
                event.setDate(cursor.getString(4));

                eventList.add(event);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return eventList;
    }

    public ArrayList<Event> getAllDateEvents(LocalDate date) {
        ArrayList<Event> eventList = new ArrayList<>();

        String searchQuery = "SELECT * FROM " + EventTable.TABLE + " where date = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setTitle(cursor.getString(1));
                event.setUser_email(cursor.getString(2));
                event.setDetails(cursor.getString(3));
                event.setDate(cursor.getString(4));

                eventList.add(event);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return eventList;
    }
}
