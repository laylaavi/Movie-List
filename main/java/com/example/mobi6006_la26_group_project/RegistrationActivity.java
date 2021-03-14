package com.example.mobi6006_la26_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobi6006_la26_group_project.Object.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    EditText etUsername, etEmail, etPhoneNumber, etAddress, etPassword, etConfirmPassword;
    TextView tvDob;
    RadioButton rbMale, rbFemale;
    CheckBox cbAgreement;
    Button btnDate, btnRegister;
    Calendar calendar;
    DatePickerDialog dpd;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHelper = new DatabaseHelper(this, DatabaseHelper.DB_NAME, null, DatabaseHelper.DB_VERSION);

        etUsername = findViewById(R.id.et_username);
        tvDob = findViewById(R.id.tv_dob);
        btnDate = findViewById(R.id.btn_dob);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        etEmail = findViewById(R.id.et_email);
        etPhoneNumber = findViewById(R.id.et_number);
        etAddress = findViewById(R.id.et_address);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirmPassword);
        cbAgreement = findViewById(R.id.cb_agreement);
        btnRegister = findViewById(R.id.btn_register);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int dobYear, int dobMonth, int dobDayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat();
                        tvDob.setText(dobDayOfMonth + "/" + (dobMonth+1) + "/" + dobYear);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
            }
        });
    }

    private void validateRegister() {
        String username, dob, gender, email, phoneNumber, address, password, confirmPassword;

        username = etUsername.getText().toString();
        dob = tvDob.getText().toString();
        email = etEmail.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        address = etAddress.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();

        if (username.length() < 5 || username.length() > 25) {
            Toast.makeText(this, "Username must be between 5-25 characters", Toast.LENGTH_SHORT).show();
        } else if (dob.length() < 8 || dob.length() > 10){
            Toast.makeText(this, "Date of birth must be selected", Toast.LENGTH_SHORT).show();
        } else if (!rbMale.isChecked() && !rbFemale.isChecked()) {
            Toast.makeText(this, "Gender must be selected", Toast.LENGTH_SHORT).show();
        } else if (!email.contains("@") || !email.endsWith(".com")) {
            Toast.makeText(this, "Email must contains only one ‘@’ and must ends with '.com'", Toast.LENGTH_SHORT).show();
        } else if (phoneNumber.length() < 8 || phoneNumber.length() > 14) {
            Toast.makeText(this, "Phone number must be between 8-14 characters", Toast.LENGTH_SHORT).show();
        } else if (!phoneNumber.startsWith("62")) {
            Toast.makeText(this, "Phone number must starts with ‘62’", Toast.LENGTH_SHORT).show();
        } else if (!address.endsWith(" street")) {
            Toast.makeText(this, "Address must ends with ‘street’", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 5) {
            Toast.makeText(this, "Password must be at least 5 characters", Toast.LENGTH_SHORT).show();
        } else if (!confirmPassword.equals(password)) {
            Toast.makeText(this, "Confirm password must be equals to password", Toast.LENGTH_SHORT).show();
        } else if (!cbAgreement.isChecked()) {
            Toast.makeText(this, "Agreement must be checked", Toast.LENGTH_SHORT).show();
        } else {
            if (rbMale.isChecked()) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            User user = new User(username, dob, gender, email, "+" + phoneNumber, address, password);
            boolean add = dbHelper.dbInsertUser(user);
            if (add == true) {
                Toast.makeText(this, "Registration is success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}