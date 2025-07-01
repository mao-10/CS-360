package com.example.eventtracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Activity activity;
    EditText login_email, login_password;
    Button login_button, signup_button;
    String email, name, password, phoneNum;
    Boolean emptyVal;
    PopupWindow popupWindow;
    SQLiteDatabase db;
    UserDatabase userDatabase;
    String temp_password = "Not_Found";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        activity = this;

        login_button = findViewById(R.id.login_button);
        signup_button = findViewById(R.id.create_button);
        login_email = findViewById(R.id.email_edit_text);
        login_password = findViewById(R.id.password_edit_text);
        userDatabase = new UserDatabase(this);

        login_button.setOnClickListener(view -> {
            LoginFunction();
        });

        signup_button.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    public void LoginFunction() {
        email = login_email.getText().toString().trim();
        String message = CheckTextNotEmpty();

        if (!emptyVal) {
            userDatabase.getWritableDatabase();

            Cursor cursor = (Cursor) userDatabase.findUserEmail(email);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();

                    temp_password = cursor.getString(2);
                    name = cursor.getString(1);
                    phoneNum = cursor.getString(4);

                    cursor.close();
                }
            }
            userDatabase.close();
            CheckFinalDetails();
        }
        else {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    public String CheckTextNotEmpty() {
        String message = "";
        email = login_email.getText().toString().trim();
        password = login_password.getText().toString().trim();

        if (email.isEmpty()) {
            login_email.requestFocus();
            emptyVal = true;
            message = "Email is empty";
        }
        else if (password.isEmpty()) {
            login_password.requestFocus();
            emptyVal = true;
            message = "Password is empty";
        }
        else {
            emptyVal = false;
        }
        return  message;
    }

    public void CheckFinalDetails() {
        password = login_password.getText().toString().trim();
        email = login_email.getText().toString().trim();
        if (temp_password.equalsIgnoreCase(password)) {
            Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();
            bundle.putString("user_name", name);
            bundle.putString("user_email", email);
            bundle.putString("user_phone", phoneNum);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            EmptyAfterInsert();
        }
        else {
            Toast.makeText(LoginActivity.this, "Incorrect email or password...Or user doesn't exist", Toast.LENGTH_LONG).show();
        }
        temp_password = "Not_Found";
    }

    public void EmptyAfterInsert() {
        login_email.getText().clear();
        login_password.getText().clear();
    }

}