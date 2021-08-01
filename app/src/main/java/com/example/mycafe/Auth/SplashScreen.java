package com.example.mycafe.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycafe.Activity.HomePage;
import com.example.mycafe.Activity.items;
import com.example.mycafe.Adapter.RecyclerAdapter;
import com.example.mycafe.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progressBar3);
        fAuth = FirebaseAuth.getInstance();

        int SPLASH_DISPLAY_LENGTH = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the MainActivity. */
                if (fAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), HomePage.class));
                    progressBar.setVisibility(View.VISIBLE);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    progressBar.setVisibility(View.VISIBLE);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}