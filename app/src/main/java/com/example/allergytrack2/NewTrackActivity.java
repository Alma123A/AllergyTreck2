package com.example.allergytrack2;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import android.widget.ArrayAdapter;


public class NewTrackActivity extends AppCompatActivity {

    private TextInputEditText dateInput, symptomsInput;
    private TextInputLayout allergyTypeLayout;
    private AutoCompleteTextView allergyTypeDropdown;
    private Slider severitySlider;
    private MaterialButton saveButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_track);

        // אתחול רכיבי המסך
        dateInput = findViewById(R.id.dateInput);
        symptomsInput = findViewById(R.id.symptomsInput);
        allergyTypeLayout = findViewById(R.id.allergyTypeLayout);
        allergyTypeDropdown = findViewById(R.id.allergyTypeDropdown);
        String[] allergyTypes = {
                "אלרגיה לאגוזים", "אלרגיה לחלב", "אלרגיה לגלוטן",
                "אלרגיה לדגים", "אלרגיה לפירות ים", "אלרגיה לסויה",
                "אלרגיה לשומשום", "אלרגיה לביצים", "אלרגיה לקטניות",
                "אלרגיה לפירות", "אלרגיה לתרופות", "אלרגיה לעקיצות חרקים"
        };

        // יצירת מתאם (Adapter) והגדרתו ל-AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allergyTypes);
        allergyTypeDropdown.setAdapter(adapter);
           severitySlider = findViewById(R.id.severitySlider);
        saveButton = findViewById(R.id.saveButton);

        // אתחול מסד הנתונים של Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("allergy_tracks");

        // הגדרת כפתור השמירה
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewTrack();
            }
        });
    }

    private void saveNewTrack() {
        // קבלת המידע מהשדות
        String date = dateInput.getText().toString().trim();
        String symptoms = symptomsInput.getText().toString().trim();
        String allergyType = allergyTypeDropdown.getText().toString().trim();
        int severity = (int) severitySlider.getValue();

        // בדיקות אם הנתונים ריקים
        if (date.isEmpty() || symptoms.isEmpty() || allergyType.isEmpty()) {
            Toast.makeText(NewTrackActivity.this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        // קבלת המשתמש המחובר
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            // יצירת מזהה ייחודי למעקב החדש
            String trackId = databaseReference.push().getKey();

            // יצירת אובייקט הנתונים
            Map<String, Object> newTrack = new HashMap<>();
            newTrack.put("userId", userId);
            newTrack.put("date", date);
            newTrack.put("symptoms", symptoms);
            newTrack.put("allergyType", allergyType);
            newTrack.put("severity", severity);

            // שליחת הנתונים ל־Realtime Database
            if (trackId != null) {
                databaseReference.child(userId).child(trackId).setValue(newTrack)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(NewTrackActivity.this, "המעקב נשמר בהצלחה!", Toast.LENGTH_SHORT).show();
                            finish(); // חזרה לעמוד הקודם
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(NewTrackActivity.this, "שגיאה בשמירה, נסה שוב", Toast.LENGTH_SHORT).show();
                        });
            }
        } else {
            Toast.makeText(NewTrackActivity.this, "לא מחובר למשתמש", Toast.LENGTH_SHORT).show();
        }
    }
}
