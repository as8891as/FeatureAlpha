package com.example.featurealpha;

import static com.example.featurealpha.FBRef.refJsonData;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class JSONActivity extends MasterActivity {

    private EditText eT_a, eT_b, eT_c;
    private static final String DOCUMENT_ID = "single_instance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonactivity);

        eT_a = findViewById(R.id.eT_a);
        eT_b = findViewById(R.id.eT_b);
        eT_c = findViewById(R.id.eT_c);
    }

    public void clearFields(View view) {
        eT_a.setText("");
        eT_b.setText("");
        eT_c.setText("");
    }

    public void saveJson(View view) {
        Map<String, Object> jsonScheme = new HashMap<>();
        jsonScheme.put("a", eT_a.getText().toString());
        jsonScheme.put("b", eT_b.getText().toString());
        jsonScheme.put("c", eT_c.getText().toString());

        refJsonData.document(DOCUMENT_ID)
                .set(jsonScheme)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Info was successfully uploaded", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void pasteJson(View view) {
        refJsonData.document(DOCUMENT_ID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String a = documentSnapshot.getString("a");
                        String b = documentSnapshot.getString("b");
                        String c = documentSnapshot.getString("c");

                        eT_a.setText(a != null ? a : "");
                        eT_b.setText(b != null ? b : "");
                        eT_c.setText(c != null ? c : "");

                        Toast.makeText(this, "Pasted from Firestore", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No saved data found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Paste failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}