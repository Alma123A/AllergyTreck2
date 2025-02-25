package com.example.allergytrack2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;


public class AITreatment extends AppCompatActivity {

    private TextView recommendationsText;
    private MaterialButton backButton;
    private String symptoms, allergyType;
    private int severity;
    private Compiler EdgeToEdge;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable();
        setContentView(R.layout.activity_aitreatment);

        recommendationsText = findViewById(R.id.recommendationText);
        backButton = findViewById(R.id.btnBackHome);

        // קבלת הנתונים מהאינטנט
        symptoms = getIntent().getStringExtra("symptoms");
        allergyType = getIntent().getStringExtra("allergyType");
        severity = getIntent().getIntExtra("severity", 1);

        // שליחת בקשה ל-AI לקבלת המלצות טיפול
        getAIRecommendations();

        // כפתור חזרה
        backButton.setOnClickListener(v -> finish());
    }

    private void getAIRecommendations() {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("symptoms", symptoms);
            jsonRequest.put("allergyType", allergyType);
            jsonRequest.put("severity", severity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonRequest.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://your-ai-api.com/getRecommendations") // להחליף ב-API המתאים
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(AITreatment.this, "שגיאה בקבלת המלצות", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(() -> recommendationsText.setText(responseData));
                }
            }
        });
    }
}
