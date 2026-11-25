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

    private CanvasView canvasView;
    private Guideline guideline1, guideline2, guideline3, guideline4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        canvasView = findViewById(R.id.sensor_canvas);
        guideline1 = findViewById(R.id.guideline1);
        guideline2 = findViewById(R.id.guideline2);
        guideline3 = findViewById(R.id.guideline3);
        guideline4 = findViewById(R.id.guideline4);

    }

    public void startSlideAnimation(View view) {
        canvasView.animate().cancel();
        canvasView.bringToFront();

        float startX = guideline2.getX() - canvasView.getWidth();
        float startY = guideline3.getY();

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