package com.example.featurealpha;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GraphicsActivity extends MasterActivity {
    private CanvasView canvasView;
    private MovingShapeView movingShapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        canvasView = findViewById(R.id.canvasView);
        movingShapeView = findViewById(R.id.movingShapeView);
    }

    public void startSlideAnimation(View view) {
        movingShapeView.animate().cancel();
        movingShapeView.bringToFront();
        float startX = canvasView.getX() + canvasView.getWidth() - movingShapeView.getWidth();
        float startY = canvasView.getY();
        float endX = canvasView.getX();
        float endY = canvasView.getY() + canvasView.getHeight() - movingShapeView.getHeight();
        movingShapeView.setX(startX);
        movingShapeView.setY(startY);
        movingShapeView.animate()
                .x(endX)
                .y(endY)
                .setDuration(1500)
                .start();
    }
}