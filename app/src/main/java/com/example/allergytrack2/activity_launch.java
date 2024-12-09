package com.example.allergytrack2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.example.allergytrack2.databinding.ActivityLaunchBinding;

public class LaunchActivity extends AppCompatActivity {

    private ActivityLaunchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide status bar for full screen experience
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getStartedButton.setOnClickListener(view -> navigateToMain());

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToMain();
            finish();
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("allergic_track_prefs", MODE_PRIVATE);
        return prefs.getBoolean("is_logged_in", false);
    }

    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity3.class));
        finish();
    }
}