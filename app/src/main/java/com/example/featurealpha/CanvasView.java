package com.example.featurealpha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {
    private Paint bgPaint;
    private Paint borderPaint;
    private float strokeWidth = 4f;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#EAEAEA"));
        bgPaint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint();
        borderPaint.setColor(Color.GRAY);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(strokeWidth);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
        float halfStroke = strokeWidth / 2f;
        canvas.drawRect(
                halfStroke,
                halfStroke,
                getWidth() - halfStroke,
                getHeight() - halfStroke,
                borderPaint
        );
    }
}