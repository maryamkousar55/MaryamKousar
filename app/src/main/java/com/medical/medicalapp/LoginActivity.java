package com.medical.medicalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText edt_email, edt_pass;
    Button btn_login;
    TextView txt_goto_sign;
    SQLiteDatabase db;
    String str1_email, str2_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        openCreateDB();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str1_email = edt_email.getText().toString().trim();
                str2_pass = edt_pass.getText().toString().trim();

                Cursor c = db.rawQuery("SELECT * FROM Register WHERE Email = '" + str1_email + "' AND Password = '" + str2_pass + "'", null);

                if (c.moveToNext()) {
                    Toast.makeText(LoginActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (str1_email.length() == 0 && str2_pass.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Enter all required data", Toast.LENGTH_SHORT).show();
                } else if (edt_email.getText().toString().length() == 0) {
                    edt_email.setError("Email not entered");
                    edt_email.requestFocus();
                } else if (edt_pass.getText().toString().length() == 0) {
                    edt_pass.setError("Password not entered");
                    edt_pass.requestFocus();
                } else if (edt_pass.getText().toString().length() < 6) {
                    edt_pass.setError("Password should be 6 characters");
                    edt_pass.requestFocus();
                } else {
                    Toast.makeText(LoginActivity.this, "Not exist, Please Register", Toast.LENGTH_SHORT).show();
                }
                clearText();

            }
        });

        txt_goto_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }


    public void openCreateDB() {
        db = openOrCreateDatabase("Registration", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Register(Id INTEGER PRIMARY KEY AUTOINCREMENT, Email VARCHAR(30), Password VARCHAR(30), Confirm_Password VARCHAR(30))");
    }

    public void init() {
        edt_email = findViewById(R.id.edt_login_email);
        edt_pass = findViewById(R.id.edt_login_pass);
        btn_login = findViewById(R.id.btn_login);
        txt_goto_sign = findViewById(R.id.goto_signup);
//        linear_fb = findViewById(R.id.linear_login_fb);
//        linear_google = findViewById(R.id.linear_login_google);
    }

    public void clearText() {
        edt_email.setText("");
        edt_pass.setText("");
    }
}