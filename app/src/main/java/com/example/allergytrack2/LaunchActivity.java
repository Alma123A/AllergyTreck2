package com.example.allergytrack2;

import android.annotation.SuppressLint; // ייבוא של מחלקה המאפשרת השתקת אזהרות בקוד (לא בשימוש בקוד הזה).
import android.content.Intent; // ייבוא של מחלקת Intent המאפשרת מעבר בין מסכים באפליקציה.
import android.content.SharedPreferences; // ייבוא של מחלקת SharedPreferences המאפשרת שמירת נתונים מקומיים.
import android.os.Bundle; // ייבוא של מחלקת Bundle להעברת מידע בין מסכים.
import android.view.WindowManager; // ייבוא לניהול תכונות חלון, כמו הסתרת סרגל הסטטוס.

import androidx.activity.EdgeToEdge; // מחלקה לניהול ממשק משתמש "מקצה לקצה" (Edge-to-Edge UI).
import androidx.appcompat.app.AppCompatActivity; // מחלקת הבסיס לפעילויות באפליקציה.
import androidx.core.graphics.Insets; // ייבוא לניהול שוליים (Insets) – לא בשימוש בקוד.
import androidx.core.view.ViewCompat; // מחלקת עזר לניהול תצוגות – לא בשימוש בקוד.
import androidx.core.view.WindowInsetsCompat; // מחלקת עזר לניהול Insetים של חלון – לא בשימוש בקוד.

import com.example.allergytrack2.databinding.ActivityLaunchBinding; // מחלקת Binding המאפשרת גישה לתצוגות בקובץ ה-XML של המסך.

public class LaunchActivity extends AppCompatActivity {
    private ActivityLaunchBinding binding; // משתנה עבור ה-Binding המאפשר גישה לתצוגות במסך.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // הפעלת ממשק משתמש "מקצה לקצה" למראה חלק יותר.
        setContentView(R.layout.activity_launch); // קביעת פריסת המסך ל-activity_launch.xml.

        // הסתרת סרגל הסטטוס לחוויית מסך מלא.
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // יצירת Binding עבור תצוגות במסך.
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // הגדרת פריסת המסך בעזרת ה-Binding.

        // מאזין ללחיצה על כפתור "Get Started" למעבר למסך הראשי.
        binding.getStartedButton.setOnClickListener(view -> navigateToMain());

        // בדיקה אם המשתמש מחובר, ואם כן – מעבר למסך הראשי וסיום המסך הנוכחי.
        if (isUserLoggedIn()) {
            navigateToMain(); // מעבר למסך הראשי.
            finish(); // סיום הפעילות הנוכחית כדי למנוע חזרה למסך זה.
        }
    }

    // בדיקה האם המשתמש מחובר באמצעות SharedPreferences.
    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("allergic_track_prefs", MODE_PRIVATE); // טעינת ההעדפות של האפליקציה.
        return prefs.getBoolean("is_logged_in", false); // בדיקה אם נשמר ערך "is_logged_in" שהוא true.
    }

    // מעבר לפעילות הראשית.
    private void navigateToMain() {
        startActivity(new Intent(this, MainActivity3.class)); // יצירת Intent למעבר ל-MainActivity3 (לוודא שקיים).
        finish(); // סיום הפעילות הנוכחית לאחר המעבר.
    }
}
