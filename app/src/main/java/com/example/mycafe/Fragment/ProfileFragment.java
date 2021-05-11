package com.example.mycafe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mycafe.Activity.Dashboard;
import com.example.mycafe.Auth.MainActivity;
import com.example.mycafe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    CardView personal, favourites, about, feedback, help, updates;
    TextView logout, verifymsg;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    Button verifybtn;
    FirebaseFirestore fStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        personal = view.findViewById(R.id.personal);
        favourites = view.findViewById(R.id.favourites);
        about = view.findViewById(R.id.about);
        feedback = view.findViewById(R.id.feedback);
        help = view.findViewById(R.id.help);
        updates = view.findViewById(R.id.updates);
        progressBar = view.findViewById(R.id.progressBar2);

        verifymsg = view.findViewById(R.id.verifymsg);
        verifybtn = view.findViewById(R.id.verifybtn);

        logout = view.findViewById(R.id.logout);

        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();


        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), Dashboard.class));
            }
        });







        if (user.isEmailVerified()) {
            verifybtn.setVisibility(View.INVISIBLE);
            verifybtn.setVisibility(View.INVISIBLE);
        } else {
            verifymsg.setVisibility(View.VISIBLE);
            verifybtn.setVisibility(View.VISIBLE);
            verifybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    //Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show();
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(view.getContext(), "Verification Email has been sent successfully", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(view.getContext(), "Error!!" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });

        }





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(view.getContext(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(view.getContext(), "Logged Out Successfully", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}
