package com.example.featurealpha;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GraphicsActivity extends MasterActivity {

    private ConstraintLayout mainLayout;
    private CanvasView canvasView;
    private Guideline guideline1, guideline2, guideline3, guideline4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graphics);

        mainLayout = findViewById(R.id.main);
        canvasView = findViewById(R.id.sensor_canvas);
        guideline1 = findViewById(R.id.guideline1);
        guideline2 = findViewById(R.id.guideline2);
        guideline3 = findViewById(R.id.guideline3);
        guideline4 = findViewById(R.id.guideline4);

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Matches android:onClick="startSlideAnimation" in activity_graphics.xml
    public void startSlideAnimation(View view) {
        canvasView.animate().cancel();
        canvasView.bringToFront();

        // Top-right corner inside guidelines 1-4
        float startX = guideline2.getX() - canvasView.getWidth();
        float startY = guideline3.getY();

        // Bottom-left corner inside guidelines 1-4
        float endX = guideline1.getX();
        float endY = guideline4.getY() - canvasView.getHeight();

        canvasView.setX(startX);
        canvasView.setY(startY);

        canvasView.animate()
                .x(endX)
                .y(endY)
                .setDuration(1500)
                .start();
    }
}