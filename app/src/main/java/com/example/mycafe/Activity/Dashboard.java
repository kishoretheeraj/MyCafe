package com.example.mycafe.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard extends AppCompatActivity {

    TextView Name, Phone, Email, Dept, OrderCount, DateCreated, Points;
    FirebaseAuth fAuth;
    Button update, changepassword;
    FirebaseFirestore fStore;
    String userId;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Name = findViewById(R.id.Name);
        Phone = findViewById(R.id.Phone);
        changepassword = findViewById(R.id.changepassword);
        Email = findViewById(R.id.Email);
        Dept = findViewById(R.id.Dept);
        update = findViewById(R.id.update);
        OrderCount = findViewById(R.id.OrderCount);
        DateCreated = findViewById(R.id.Datecreated);
        Points = findViewById(R.id.Points);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        userId = user.getUid();


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(Dashboard.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Name.setText(documentSnapshot.getString("Name"));
                Email.setText(documentSnapshot.getString("Email"));
                Phone.setText(documentSnapshot.getString("Mobile"));
                DateCreated.setText(documentSnapshot.getString("Datecreated"));
                Dept.setText(documentSnapshot.getString("Department"));

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, UpdateProfile.class);
                intent.putExtra("Name", Name.getText().toString());
                intent.putExtra("Email", Email.getText().toString());
                intent.putExtra("Mobile", Phone.getText().toString());
                intent.putExtra("Dept", Dept.getText().toString());
                startActivity(intent);
                finish();
            }
        });


    }

    public void change(View view) {
        final EditText resetpassword = new EditText(view.getContext());
        final AlertDialog.Builder passworddialog = new AlertDialog.Builder(view.getContext());
        passworddialog.setTitle("Reset Password");
        passworddialog.setMessage("Enter your New Password :-");
        passworddialog.setView(resetpassword);

        passworddialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String newpassword = resetpassword.getText().toString();


                user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Dashboard.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Dashboard.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
        passworddialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        passworddialog.show();
    }
}