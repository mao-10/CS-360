package com.example.eventtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.Objects;

public class UserDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int VERSION = 1;

    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class UserTable {
        private static final String TABLE = "Users";
        private static final String USER_ID = "_id";
        private static final String USER_NAME = "name";
        private static final String USER_PASS = "password";
        private static final String USER_EMAIL = "email";
        private static final String USER_PHONE = "phoneNumber";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.USER_ID + " integer primary key autoincrement, " +
                UserTable.USER_NAME + " text, " +
                UserTable.USER_PASS + " text, " +
                UserTable.USER_EMAIL + " text, " +
                UserTable.USER_PHONE + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + UserTable.TABLE);
        onCreate(db);
    }

    // add a user to database
    public Boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.USER_NAME, user.getUserName());
        values.put(UserTable.USER_PASS, user.getUserPass());
        values.put(UserTable.USER_EMAIL, user.getUserEmail());
        values.put(UserTable.USER_PHONE, user.getUserPhone());

        long result = db.insert(UserTable.TABLE, null, values);
        Boolean boolVal = null;
        if (result == -1) {
            boolVal = false;
        }
        else {
            boolVal = true;
        }
        return boolVal;
    }

    // read a user from database
    public User readUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + UserTable.TABLE + " where email = ? and password = ?";

        Cursor cursor = db.rawQuery(sql,
                new String[] {UserTable.USER_EMAIL, UserTable.USER_PASS});

        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User(cursor.getString(1),
                cursor.getString(2), cursor.getString(3),
                cursor.getString(4));

        cursor.close();
        return user;
    }

    public User findUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "select * from " + UserTable.TABLE + " where email = ?";

        Cursor cursor = db.rawQuery(sql,
                new String[]{UserTable.USER_EMAIL});

        User user = new User(cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        cursor.close();
        return user;
    }
}
