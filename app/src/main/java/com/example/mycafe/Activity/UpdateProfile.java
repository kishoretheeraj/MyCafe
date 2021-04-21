package com.example.mycafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {
    EditText Name, Mobile, Email, Dept;
    TextView deleteUser;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Button update;
    ProgressBar progressBar;
    boolean isEmailValid;
    String emailPattern = "[a-z.]*+[a-z.]*+[0-9.]*+[a-z]+@ritchennai.edu.in$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Name = findViewById(R.id.Name);
        Mobile = findViewById(R.id.Mobile);
        Email = findViewById(R.id.Email);
        progressBar = findViewById(R.id.progressBar3);
        Dept = findViewById(R.id.Dept);
        deleteUser = findViewById(R.id.delete);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        update = findViewById(R.id.updatebtn);


        user = fAuth.getCurrentUser();

        Intent data = getIntent();
        String name = data.getStringExtra("Name");
        final String email = data.getStringExtra("Email");
        String mobile = data.getStringExtra("Mobile");
        String dept = data.getStringExtra("Dept");

        Name.setText(name);
        Email.setText(email);
        Mobile.setText(mobile);
        Dept.setText(dept);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname = Name.getText().toString().trim();
                final String department = Dept.getText().toString().trim();
                final String emailid = Email.getText().toString().trim();
                final String mobileno = Mobile.getText().toString().trim();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                if (TextUtils.isEmpty(fullname)) {
                    Name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(department)) {
                    Dept.setError("Department is required");
                    return;
                }
                if (TextUtils.isEmpty(mobileno)) {
                    Mobile.setError("Mobile Number is required");
                    return;
                }
                if (emailid.isEmpty()) {
                    Email.setError("Enter email Address");
                    isEmailValid = false;
                } else {
                    if (emailid.matches(emailPattern)) {
                        isEmailValid = true;
                    } else {
                        isEmailValid = false;
                        Email.setError("Enter College Mail ID");
                        Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    }

                }

                if (mobileno.length() < 10) {
                    Mobile.setError("Mobile Number Shouldn't be less than 10 Digits");
                    return;
                }

                if (isEmailValid) {
                    progressBar.setVisibility(View.VISIBLE);
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference docref = fStore.collection("users").document(user.getUid());
                            Map<String, Object> edited = new HashMap<>();
                            edited.put("Name", fullname);
                            edited.put("Email", emailid);
                            edited.put("Mobile", mobileno);
                            edited.put("Department", department);
                            docref.update(edited);
                            startActivity(new Intent(getApplicationContext(), Dashboard.class));
                            finish();
                            Toast.makeText(UpdateProfile.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Email.setError("Enter College Mail ID Only");
                }


            }
        });


        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProfile.this, "User account deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpdateProfile.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }


}