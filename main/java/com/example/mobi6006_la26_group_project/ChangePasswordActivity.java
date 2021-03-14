package com.example.mobi6006_la26_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText etOldPassword, etNewPassword,etConfirmNewPassword;
    Button btnUpdatePassword;
    DatabaseHelper dbHelper;
    int userId;
    String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        dbHelper = new DatabaseHelper(this, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION);

        etOldPassword = findViewById(R.id.et_currentPassword);
        etNewPassword = findViewById(R.id.et_newPassword);
        etConfirmNewPassword = findViewById(R.id.et_confirmNewPassword);
        btnUpdatePassword = findViewById(R.id.btn_updatePassword);

        userId = getIntent().getIntExtra("userId",0);
        currentPassword = getIntent().getStringExtra("currentPassword");

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUpdatePassword();
            }
        });
    }

    private void validateUpdatePassword() {
        String oldPassword, newPassword, confirmNewPassword;

        oldPassword = etOldPassword.getText().toString();
        newPassword = etNewPassword.getText().toString();
        confirmNewPassword = etConfirmNewPassword.getText().toString();

        if (!oldPassword.equals(currentPassword)) {
            Toast.makeText(this, "Old password must be the same with current password", Toast.LENGTH_LONG).show();
        }
        else if (oldPassword.equals(currentPassword)) {
            if (newPassword.length() < 5) {
                Toast.makeText(this, "New password must be at least 5 characters", Toast.LENGTH_LONG).show();
            }
            else if (!confirmNewPassword.equals(newPassword)) {
                Toast.makeText(this, "Confirm password and new password must be the same", Toast.LENGTH_LONG).show();
            }
            else {
                dbHelper.dbUpdateUser(userId, newPassword);
                Toast.makeText(this, "New password is updated!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ChangePasswordActivity.this, HomeActivity.class);
                intent.putExtra("id", userId);
                intent.putExtra("currentPassword", newPassword);
                startActivity(intent);
                finish();
            }
        }
    }
}