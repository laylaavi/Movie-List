package com.example.mobi6006_la26_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnSignIn, btnSignUp;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateSignIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void validateSignIn() {
        Cursor cursor = dbHelper.getUserData();
        String inputUsername, inputPassword;
        inputUsername = etUsername.getText().toString();
        inputPassword = etPassword.getText().toString();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(7);

            if (inputUsername.isEmpty()) {
                Toast.makeText(this, "Username must be filled", Toast.LENGTH_SHORT).show();
            } else if (inputPassword.isEmpty()) {
                Toast.makeText(this, "Password must be filled", Toast.LENGTH_SHORT).show();
            } else if (!inputUsername.equals(username) || !inputPassword.equals(password)) {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            } else {
                if (inputUsername.equals(username) && inputPassword.equals(password)) {
                    Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("userId", id);
                    intent.putExtra("currentPassword", password);
                    startActivity(intent);
                    finish();
                }
                break;
            }
        }
    }

    private void signUp() {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}