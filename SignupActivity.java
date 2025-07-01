package com.example.eventtracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    UserDatabase userDatabase;
    SQLiteDatabase db;
    Button signup_button;
    EditText signup_email, signup_password, confirm_text, signup_phone, signup_username;
    TextView loginRedirectText;
    Boolean emptyBool, confirmBool;
    String result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_phone = findViewById(R.id.signup_phone);
        signup_username = findViewById(R.id.signup_username);
        confirm_text = findViewById(R.id.confirm_text);
        signup_button = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        userDatabase = new UserDatabase(this);

        signup_button.setOnClickListener(view -> {
            String message = CheckTextNotEmpty();
            if (!emptyBool && !confirmBool) {
                CheckEmailExists();
                EmptyAfterInsert();
            }
            else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public String CheckTextNotEmpty() {
        String message = "";
        String email = signup_email.getText().toString().trim();
        String pass = signup_password.getText().toString().trim();
        String name = signup_username.getText().toString().trim();
        String phoneNum = signup_phone.getText().toString().trim();
        String confirm = confirm_text.getText().toString().trim();

        if (email.isEmpty()) {
            signup_email.requestFocus();
            emptyBool = true;
            message = "Email is empty";
        }
        else if (pass.isEmpty()) {
            signup_password.requestFocus();
            emptyBool = true;
            message = "Password is empty";
        }
        else if (confirm.isEmpty()) {
            confirm_text.requestFocus();
            emptyBool = true;
            message = "Confirmation password is empty";
        }
        else if (name.isEmpty()) {
            signup_username.requestFocus();
            emptyBool = true;
            message = "Username is empty";
        }
        else if (phoneNum.isEmpty()) {
            signup_phone.requestFocus();
            emptyBool = true;
            message = "Phone number is empty";
        }
        else {
            emptyBool = false;
        }
        boolean val = CheckConfirmMatches();
        if (val) {
            confirmBool = true;
        }
        return message;
    }

    public void CheckEmailExists() {
        String email = signup_email.getText().toString().trim();
        userDatabase.getWritableDatabase();
        Cursor cursor = (Cursor) userDatabase.findUserEmail(email);

        while(cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                result = "Email_Found";
                cursor.close();
            }
        }
        userDatabase.close();

        CheckFinalDetails();

    }

    public void EmptyAfterInsert() {
        signup_email.getText().clear();
        signup_password.getText().clear();
        signup_username.getText().clear();
        signup_phone.getText().clear();
    }

    public Boolean CheckConfirmMatches() {
        Boolean val = null;
        String pass = signup_email.getText().toString().trim();
        String confirm = confirm_text.getText().toString().trim();

        if (!pass.equals(confirm)) {
            confirm_text.requestFocus();
            emptyBool = true;
            val = false;
        }
        return val;
    }

    public void CheckFinalDetails() {
        if(result.equalsIgnoreCase("Email_Found")) {
            Toast.makeText(SignupActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
        }
        else {
            InsertUser();
        }
        result = "Not_Found";
    }

    public void InsertUser() {
        String email = signup_email.getText().toString().trim();
        String pass = signup_password.getText().toString().trim();
        String name = signup_username.getText().toString().trim();
        String phone = signup_phone.getText().toString().trim();

        User user = new User(name, pass, email, phone);
        userDatabase.addUser(user);

        Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();

        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        this.finish();
    }

}