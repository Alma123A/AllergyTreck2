package com.example.allergytrack2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        MaterialButton btnNewTrack = findViewById(R.id.btnNewTrack);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        btnNewTrack.setOnClickListener(v -> {
            // מעבר למסך יצירת מעקב חדש
            startActivity(new Intent(this, NewTrackActivity.class));
        });

        btnLogin.setOnClickListener(v -> {
            // מעבר למסך התחברות
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}