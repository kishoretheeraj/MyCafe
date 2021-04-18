package com.example.mycafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText userName, userPassword;
    Button login;
    FirebaseAuth fAuth;
    TextView newuser;
    ProgressBar progressBar;
    boolean isEmailValid;
    String emailPattern = "[a-z.]*+[a-z.]*+[0-9.]*+[a-z]+@ritchennai.edu.in$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        login = findViewById(R.id.login);
        newuser = findViewById(R.id.newuser);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);

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
        if (userName.getText().toString().isEmpty()) {
            userName.setError("Enter email Address");
            isEmailValid = false;
        } else {
            if (userName.getText().toString().trim().matches(emailPattern)) {
                isEmailValid = true;
            } else {
                isEmailValid = false;
                userName.setError("Enter College Mail ID");
                Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
            }

        }


        if (isEmailValid) {

            String email = userName.getText().toString();
            String password = userPassword.getText().toString();
            if (TextUtils.isEmpty(password)) {
                userPassword.setError("Password is required");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Error!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        } else {
            progressBar.setVisibility(View.GONE);
            userName.setError("Enter College Mail ID");
        }
    }
    public void forgot(View view) {
        final EditText resetmail1=new EditText(view.getContext());
        final AlertDialog.Builder passworddialog=new AlertDialog.Builder(view.getContext());
        passworddialog.setTitle("Reset Password");
        passworddialog.setMessage("Enter your Email id to Receive Reset link :-");
        passworddialog.setView(resetmail1);

        passworddialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //get email and send link
                if (resetmail1.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Your College Mail ID", Toast.LENGTH_SHORT).show();
                } else {
                    String mail = resetmail1.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Reset link as been sent", Snackbar.LENGTH_LONG);
                            snackbar.show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error!!" + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });
        passworddialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passworddialog.show();
    }
    public void onBackPressed() {
        finish();
    }
}