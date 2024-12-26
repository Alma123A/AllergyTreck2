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
        setContentView(R.layout.activity_main3);

        // הצגת הודעת Toast
        Toast.makeText(this, "יצירת מעקב חדש", Toast.LENGTH_SHORT).show();

        MaterialButton btnNewTrack = findViewById(R.id.btnNewTrack);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        if (btnNewTrack != null) {
            btnNewTrack.setOnClickListener(v -> {
                // מעבר למסך יצירת מעקב חדש
                startActivity(new Intent(this, NewTrackActivity.class));
            });
        } else {
            Toast.makeText(this, "כפתור יצירת מעקב חדש לא נמצא", Toast.LENGTH_SHORT).show();
        }

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                // מעבר למסך התחברות
                startActivity(new Intent(this, LoginActivity.class));
            });
        } else {
            Toast.makeText(this, "כפתור התחברות לא נמצא", Toast.LENGTH_SHORT).show();
        }

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