package com.example.mycafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView BackToLogin;
    EditText firstName, lastName, phoneNumber, emailAddress, firstPassword, secondPassword;
    Button registerBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BackToLogin = findViewById(R.id.BackToLogin);
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        phoneNumber = findViewById(R.id.PhoneNo);
        emailAddress = findViewById(R.id.EmailAddress);
        firstPassword = findViewById(R.id.CreatePassword);
        secondPassword = findViewById(R.id.RetypePassword);
        registerBtn = findViewById(R.id.Register);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = firstName.getText().toString().trim();
                final String lastname = lastName.getText().toString().trim();
                final String email = emailAddress.getText().toString().trim();
                final String mobile = phoneNumber.getText().toString().trim();
                final String firstpassword = firstPassword.getText().toString();
                final String secondpassword = secondPassword.getText().toString();
                String finalpassword = null;


                if (TextUtils.isEmpty(firstname)) {
                    firstName.setError("First Name is required");
                    return;
                }
                if (TextUtils.isEmpty(lastname)) {
                    lastName.setError("Last Name is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailAddress.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(mobile)) {
                    phoneNumber.setError("Mobile Number is required");
                    return;
                }
                if (mobile.length() < 10) {
                    phoneNumber.setError("Mobile Number Shouldn't be less than 10 Digits");
                    return;
                }
                if (TextUtils.isEmpty(firstpassword)) {
                    firstPassword.setError("Password Required");
                    return;
                }
                if (TextUtils.isEmpty(secondpassword)) {
                    secondPassword.setError("Password Required");
                    return;
                }
                if (firstpassword.length() < 6) {
                    firstPassword.setError("Password should be greater than six letters");
                    return;
                }

                if (!firstpassword.equals(secondpassword)) {
                    Toast.makeText(RegisterActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();

                } else {
                    finalpassword = firstpassword;

                }

                progressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email, finalpassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                            finish();

                            //send verification link
                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email has been sent successfully", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Error!!" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Error!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });


    }
}