package com.example.mycafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView newuser;
    boolean isEmailValid, isPasswordValid;
    String emailPattern = "@ritchennai.edu.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        newuser = (TextView) findViewById(R.id.newuser);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetValidation() {
        if (username.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter email Address", Toast.LENGTH_SHORT).show();
            isEmailValid = false;
        } else {
            if (username.getText().toString().trim().contains(emailPattern)) {
                isEmailValid = true;
            } else {
                isEmailValid = false;
                Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
            }

        }

        if (isEmailValid) {
            Intent intent = new Intent(getApplicationContext(), HomePage.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "UnSuccesful Login", Toast.LENGTH_SHORT).show();
        }

    }
}