package com.example.allergytrack2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.allergytrack2.databinding.ActivityLaunchBinding;



public class LaunchActivity extends AppCompatActivity {
    private ActivityLaunchBinding binding; // Corrected variable name for the binding object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_launch);


        // Hide the status bar for a full-screen experience
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Inflate the layout using View Binding
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the button click listener
        binding.getStartedButton.setOnClickListener(view -> navigateToMain());

        // If the user is logged in, navigate to the main screen immediately
        if (isUserLoggedIn()) {
            navigateToMain();
            finish();
        }
    }

    // Check if the user is logged in using SharedPreferences
    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("allergic_track_prefs", MODE_PRIVATE);
        return prefs.getBoolean("is_logged_in", false);
    }

    // Navigate to the main activity
    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity3.class)); // Ensure MainActivity3 exists
        finish();
    }
}
