package com.example.featurealpha;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class SensorsActivity extends MasterActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView tV_magnitude;
    private Button btn_listen;
    private boolean isListening = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        tV_magnitude = findViewById(R.id.tV_magnitude);
        btn_listen = findViewById(R.id.btn_listen);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (accelerometer == null) {
            tV_magnitude.setText("Accelerometer not available on this device.");
        }
    }

    // Matches android:onClick="toggleListening" in activity_sensors.xml
    public void toggleListening(View view) {
        if (accelerometer == null) {
            Toast.makeText(this, "No accelerometer found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isListening) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            isListening = true;
            btn_listen.setText("Click to stop listening");
        } else {
            stopSensor();
        }
    }

    private void stopSensor() {
        if (sensorManager != null && isListening) {
            sensorManager.unregisterListener(this);
            isListening = false;
            btn_listen.setText("Click to start listening");
            tV_magnitude.setText("Stopped");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calculate total acceleration and subtract Earth's gravity so a still phone reads ~0.00
            double totalAcceleration = Math.sqrt(x * x + y * y + z * z);
            double shakeIntensity = Math.abs(totalAcceleration - SensorManager.GRAVITY_EARTH);

            tV_magnitude.setText(String.format(Locale.US, "Shake Intensity:\n%.2f m/s²", shakeIntensity));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for basic shake detection
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopSensor();
    }
}