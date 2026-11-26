package com.example.featurealpha;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

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
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiactivity);
        eT = findViewById(R.id.eT);
        tV = findViewById(R.id.tV);
        tV.setMovementMethod(new ScrollingMovementMethod());
        GenerativeModel gm = new GenerativeModel("gemini-3.5-flash-lite", BuildConfig.GEMINI_API_KEY);
        model = GenerativeModelFutures.from(gm);
    }

    public void sendPrompt(View view) {
        String promptText = eT.getText().toString();
        if (promptText.isEmpty()) {
            Toast.makeText(this, "Please enter a prompt first", Toast.LENGTH_SHORT).show();
            return;
        }

        tV.scrollTo(0, 0);
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
                tV.scrollTo(0, 0);
            }
            @Override
            public void onFailure(Throwable t) {
                String msg = t.getMessage() != null ? t.getMessage() : "";
                tV.setText("Error: " + msg);
            }
        }, mainExecutor);
    }
}