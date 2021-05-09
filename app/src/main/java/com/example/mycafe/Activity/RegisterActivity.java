package com.example.mycafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView BackToLogin;
    EditText firstName, Dept, phoneNumber, emailAddress, firstPassword, secondPassword;
    Button registerBtn;
    FirebaseAuth fAuth;
    String userid;
    ProgressBar progressBar;
    FirebaseFirestore mFireStore;
    boolean isEmailValid;
    String thisDate;
    String emailPattern = "[a-z.]*+[a-z.]*+[0-9.]*+[a-z]+@ritchennai.edu.in$";
    ArrayList<String> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BackToLogin = findViewById(R.id.BackToLogin);
        firstName = findViewById(R.id.Name);
        Dept = findViewById(R.id.Dept);
        phoneNumber = findViewById(R.id.PhoneNo);
        emailAddress = findViewById(R.id.EmailAddress);
        firstPassword = findViewById(R.id.CreatePassword);
        secondPassword = findViewById(R.id.RetypePassword);
        registerBtn = findViewById(R.id.Register);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        mFireStore = FirebaseFirestore.getInstance();


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        thisDate = currentDate.format(todayDate);
        Toast.makeText(this, "" + thisDate, Toast.LENGTH_SHORT).show();


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
                final String Department = Dept.getText().toString().trim();
                final String email = emailAddress.getText().toString().trim();
                final String mobile = phoneNumber.getText().toString().trim();
                final String firstpassword = firstPassword.getText().toString();
                final String secondpassword = secondPassword.getText().toString();
                String finalpassword = null;
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);


                if (TextUtils.isEmpty(firstname)) {
                    firstName.setError("First Name is required");
                    return;
                }
                if (TextUtils.isEmpty(Department)) {
                    Dept.setError("Department is required");
                    return;
                }

                /*if (TextUtils.isEmpty(email)) {
                    emailAddress.setError("Email is required");
                    return;
                }*/

                if (TextUtils.isEmpty(mobile)) {
                    phoneNumber.setError("Mobile Number is required");
                    return;
                }
                if (mobile.length() < 10) {
                    phoneNumber.setError("Mobile Number Shouldn't be less than 10 Digits");
                    return;
                }
                if (emailAddress.getText().toString().isEmpty()) {
                    emailAddress.setError("Enter email Address");
                    isEmailValid = false;
                } else {
                    if (emailAddress.getText().toString().trim().matches(emailPattern)) {
                        isEmailValid = true;
                    } else {
                        isEmailValid = false;
                        emailAddress.setError("Enter College Mail ID");
                        Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    }

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


                if (isEmailValid & firstpassword.equals(secondpassword)) {

                    progressBar.setVisibility(View.VISIBLE);
                    final String finalPassword = finalpassword;
                    fAuth.createUserWithEmailAndPassword(email, finalpassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                userid = fAuth.getCurrentUser().getUid();

                                Map<String, Object> usermap = new HashMap<>();
                                usermap.put("Id", userid);
                                usermap.put("Datecreated", thisDate);
                                usermap.put("Name", firstname);
                                usermap.put("Department", Department.toUpperCase());
                                usermap.put("Email", email);
                                usermap.put("Mobile", mobile);
                                usermap.put("Password", finalPassword);
                                usermap.put("order Id", orders);


                                DocumentReference documentReference = mFireStore.collection("users").document(userid);

                                documentReference.set(usermap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
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
                } else {
                    if (!firstpassword.equals(secondpassword)) {
                        firstPassword.setError("Password Mismatch");
                    } else {
                        emailAddress.setError("Enter College Mail ID Only");
                    }
                }

            }
        });


    }
}