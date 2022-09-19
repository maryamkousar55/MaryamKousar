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

public class RegisterActivity extends AppCompatActivity {

    EditText edt_email, edt_pass, edt_Repass;
    Button btn_sign;
    TextView txt_goto_login;
    SQLiteDatabase db;
    String str1_email, str2_pass, str3_Repass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        openCreateDB();

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str1_email = edt_email.getText().toString().trim();
                str2_pass = edt_pass.getText().toString().trim();
                str3_Repass = edt_Repass.getText().toString().trim();

                Cursor c = db.rawQuery("SELECT * FROM Register WHERE Email = '" + str1_email + "' AND Password = '" + str2_pass + "'", null);
                if (c.moveToNext()) {
                    edt_email.setError("Email already exits");
                } else if (edt_email.getText().toString().length() == 0) {
                    edt_email.setError("Email not entered");
                    edt_email.requestFocus();
                } else if (edt_pass.getText().toString().length() == 0) {
                    edt_pass.setError("Password not entered");
                    edt_pass.requestFocus();
                } else if (edt_Repass.getText().toString().length() == 0) {
                    edt_Repass.setError("Please confirm password");
                    edt_Repass.requestFocus();
                } else if (!edt_pass.getText().toString().equals(edt_Repass.getText().toString())) {
                    edt_Repass.setError("Password Not matched");
                    edt_Repass.requestFocus();
                } else if (edt_pass.getText().toString().length() < 6) {
                    edt_pass.setError("Password should be 6 characters");
                    edt_pass.requestFocus();
                } else if (str1_email.length() == 0 && str2_pass.length() == 0 && str3_Repass.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Enter all required data", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("INSERT INTO Register(Email, Password, Confirm_Password) VALUES ('" + str1_email + "','" + str2_pass + "','" + str3_Repass + "')");
                    Toast.makeText(RegisterActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    clearText();
                }
            }
        });

        txt_goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    public void openCreateDB() {
        db = openOrCreateDatabase("Registration", MODE_PRIVATE, null);
    }

    public void init() {
        edt_email = findViewById(R.id.edt_sign_email);
        edt_pass = findViewById(R.id.edt_sign_pass);
        edt_Repass = findViewById(R.id.edt_sign_Repass);
        btn_sign = findViewById(R.id.btn_signup);
        txt_goto_login = findViewById(R.id.goto_login);
    }

    public void clearText() {
        edt_email.setText("");
        edt_pass.setText("");
        edt_Repass.setText("");
    }

}