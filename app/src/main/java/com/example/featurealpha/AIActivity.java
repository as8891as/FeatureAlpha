package com.example.featurealpha;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;

public class AIActivity extends MasterActivity {

    private EditText eT;
    private TextView tV;
    private ScrollView scrollView;
    private GenerativeModelFutures model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiactivity);

        eT = findViewById(R.id.eT);
        tV = findViewById(R.id.tV);
        scrollView = findViewById(R.id.scrollView);

        // Initialize Gemini (use "gemini-3.5-flash-lite" if gemini-3.8-flash is temporarily busy)
        GenerativeModel gm = new GenerativeModel("gemini-3.5-flash", BuildConfig.GEMINI_API_KEY);
        model = GenerativeModelFutures.from(gm);
    }

    // Matches android:onClick="sendPrompt" in activity_aiactivity.xml
    public void sendPrompt(View view) {
        String promptText = eT.getText().toString().trim();
        if (promptText.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt first", Toast.LENGTH_SHORT).show();
            return;
        }

        scrollView.scrollTo(0, 0);
        tV.setText("Waiting for AI...");

        Content content = new Content.Builder()
                .addText(promptText)
                .build();

        Executor mainExecutor = ContextCompat.getMainExecutor(this);
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                tV.setText(resultText != null ? resultText : "No response received.");
                scrollView.scrollTo(0, 0);
            }

            @Override
            public void onFailure(Throwable t) {
                String msg = t.getMessage() != null ? t.getMessage() : "";
                if (msg.contains("503") || msg.contains("UNAVAILABLE")) {
                    tV.setText("Server is temporarily busy (high demand). Please tap Send again.");
                } else {
                    tV.setText("Error: " + msg);
                }
            }
        }, mainExecutor);
    }
}