package com.example.allergytrack2;

import android.content.Intent; // ייבוא מחלקת Intent לצורך מעבר בין מסכים.
import android.os.Bundle; // ייבוא מחלקת Bundle לניהול מידע בין מסכים.
import android.util.Log; // ייבוא מחלקת Log לרישום הודעות ל-Logcat.
import android.widget.TextView; // ייבוא מחלקת TextView להצגת טקסטים.
import android.widget.Toast; // ייבוא מחלקת Toast להצגת הודעות למשתמש.

import androidx.activity.EdgeToEdge; // מחלקה המאפשרת יצירת ממשק "מקצה לקצה".
import androidx.activity.result.ActivityResult; // מחלקה המייצגת תוצאה שהוחזרה מפעולה שהופעלה.
import androidx.activity.result.ActivityResultCallback; // ממשק לחזרה מתוצאה של פעולה.
import androidx.activity.result.ActivityResultLauncher; // מחלקה המאפשרת הפעלת פעילות חיצונית וקבלת תוצאה.
import androidx.activity.result.contract.ActivityResultContracts; // חוזה המגדיר את סוג הפעילות החיצונית.
import androidx.annotation.NonNull; // אנוטציה לציון פרמטרים או ערכים שלא יכולים להיות null.
import androidx.appcompat.app.AppCompatActivity; // מחלקת בסיס לפעילויות באפליקציה.

import com.bumptech.glide.Glide; // ספריית Glide לטעינת תמונות.
import com.google.android.gms.auth.api.signin.GoogleSignIn; // מחלקה המאפשרת התחברות עם חשבון Google.
import com.google.android.gms.auth.api.signin.GoogleSignInAccount; // מחלקה המייצגת חשבון Google מחובר.
import com.google.android.gms.auth.api.signin.GoogleSignInClient; // מחלקה לניהול לקוח Google Sign-In.
import com.google.android.gms.auth.api.signin.GoogleSignInOptions; // מחלקה להגדרת אפשרויות התחברות ל-Google.
import com.google.android.gms.common.SignInButton; // כפתור מובנה להתחברות Google.
import com.google.android.gms.common.api.ApiException; // טיפול בחריגות בממשק Google API.
import com.google.android.gms.tasks.Task; // מחלקה לניהול פעולות אסינכרוניות.
import com.google.firebase.FirebaseApp; // ייבוא FirebaseApp לאתחול Firebase.
import com.google.firebase.auth.AuthCredential; // ייבוא אישורי התחברות.
import com.google.firebase.auth.AuthResult; // מחלקה לתוצאות התחברות Firebase.
import com.google.firebase.auth.FirebaseAuth; // מחלקה לניהול התחברות Firebase.
import com.google.firebase.auth.GoogleAuthProvider; // מחלקה המספקת אישורים לחשבון Google.
import com.google.firebase.database.FirebaseDatabase; // מסד הנתונים של Firebase.
import com.google.android.material.imageview.ShapeableImageView; // מחלקה להצגת תמונות בצורה עגולה.

import java.util.Objects; // מחלקה לטיפול בערכים של אובייקטים.

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth; // משתנה לניהול חיבור המשתמש דרך Firebase.
    GoogleSignInClient googleSignInClient; // משתנה לניהול התחברות Google.
    ShapeableImageView imageView; // משתנה להצגת תמונת המשתמש.
    TextView name, mail; // משתנים להצגת שם המשתמש והמייל.

    // מנהל תוצאה לפעילות התחברות Google.
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e("onActivityResult", result.toString()); // הדפסת התוצאה ל-Logcat.

                    if (result.getResultCode() == RESULT_OK) { // בדיקה אם הפעולה הצליחה.
                        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class); // קבלת החשבון המחובר.
                            AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null); // יצירת אישור התחברות עם חשבון Google.

                            auth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) { // התחברות הצליחה.
                                    auth = FirebaseAuth.getInstance(); // אתחול מחודש של FirebaseAuth.
                                    FirebaseDatabase database = FirebaseDatabase.getInstance(); // יצירת מופע למסד הנתונים.
                                    String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid(); // שליפת מזהה המשתמש.

                                    // יצירת אובייקט משתמש מותאם אישית.
                                    User user = new User(
                                            userId,
                                            auth.getCurrentUser().getDisplayName(),
                                            auth.getCurrentUser().getEmail()
                                    );

                                    // שמירת פרטי המשתמש למסד הנתונים.
                                    database.getReference("Users").child(userId).setValue(user);

                                    // טעינת תמונת המשתמש.
                                    Glide.with(LoginActivity.this)
                                            .load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl())
                                            .into(imageView);

                                    // הצגת שם המשתמש והמייל בתצוגה.
                                    name.setText(auth.getCurrentUser().getDisplayName());
                                    mail.setText(auth.getCurrentUser().getEmail());

                                    // הודעה למשתמש על הצלחת ההתחברות.
                                    Toast.makeText(LoginActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();

                                    // מעבר למסך NewTrackActivity עם פרמטר שם המשתמש.
                                    Intent intent = new Intent(LoginActivity.this, NewTrackActivity.class);
                                    intent.putExtra("USERNAME", auth.getCurrentUser().getDisplayName());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show(); // הודעת שגיאה.
                                }
                            });
                        } catch (ApiException e) { // טיפול בחריגות.
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // הפעלת ממשק "מקצה לקצה".
        setContentView(R.layout.activity_log_in); // קביעת פריסת המסך.

        Log.e("onCreate", "onCreate"); // רישום קריאה ל-onCreate ב-Logcat.
        FirebaseApp.initializeApp(this); // אתחול Firebase.

        // שיוך משתני התצוגה לאובייקטים במסך.
        imageView = findViewById(R.id.profileImage);
        name = findViewById(R.id.nameTv);
        mail = findViewById(R.id.mailTv);

        // הגדרת אפשרויות התחברות ל-Google.
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id)) // בקשה ל-IdToken.
                .requestEmail() // בקשה למייל המשתמש.
                .build();

        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, options); // יצירת מופע GoogleSignInClient.
        auth = FirebaseAuth.getInstance(); // אתחול FirebaseAuth.

        // שיוך מאזין לכפתור התחברות Google.
        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent(); // יצירת כוונה להתחברות.
            activityResultLauncher.launch(intent); // הפעלת תהליך התחברות.
        });
    }
}
