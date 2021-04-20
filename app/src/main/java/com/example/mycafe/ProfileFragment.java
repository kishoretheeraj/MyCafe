package com.example.mycafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    CardView personal, favourites, about, feedback, help, updates;
    TextView logout;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

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

        logout = view.findViewById(R.id.logout);
        fAuth = FirebaseAuth.getInstance();

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
