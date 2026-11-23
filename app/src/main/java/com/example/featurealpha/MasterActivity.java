package com.example.featurealpha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MasterActivity extends AppCompatActivity {
    Intent si;

    private static BroadcastReceiver networkReceiver;
    private static Boolean lastNotifiedState = null;
    private static boolean isAppVisible = false;

    @Override
    protected void onResume() {
        super.onResume();
        isAppVisible = true;


        if (networkReceiver != null) {
            checkAndNotifyConnectionChange(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAppVisible = false;
    }

    @SuppressWarnings("deprecation")
    protected static void startInternetListener(Context context) {
        if (networkReceiver != null) {
            Toast.makeText(context, "Already listening", Toast.LENGTH_SHORT).show();
            return;
        }

        lastNotifiedState = isInternetAvailable(context);

        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                checkAndNotifyConnectionChange(ctx);
            }
        };

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(networkReceiver, filter);
        Toast.makeText(context, "Internet listener turned ON", Toast.LENGTH_SHORT).show();
    }

    protected static void stopInternetListener(Context context) {
        if (networkReceiver != null) {
            context.getApplicationContext().unregisterReceiver(networkReceiver);
            networkReceiver = null;
            lastNotifiedState = null;
            Toast.makeText(context, "Internet listener turned OFF", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Listener is already OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private static void checkAndNotifyConnectionChange(Context context) {
        if (!isAppVisible) {
            return;
        }

        boolean connected = isInternetAvailable(context);
        if (lastNotifiedState == null || lastNotifiedState != connected) {
            lastNotifiedState = connected;
            String status = connected ? "Internet is ON" : "Internet is OFF";
            Toast.makeText(context.getApplicationContext(), status, Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        Network activeNetwork = cm.getActiveNetwork();
        if (activeNetwork == null) return false;

        NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.activity1) {
            si = new Intent(this, MainActivity.class);
            startActivity(si);
        } else if (id == R.id.activity2) {
            si = new Intent(this, CameraActivity.class);
            startActivity(si);
        } else if (id == R.id.activity3) {
            si = new Intent(this, BroadcastReceiverActivity.class);
            startActivity(si);
        }
        else if (id == R.id.activity4) {
            si = new Intent(this, JSONActivity.class);
            startActivity(si);
        }
        else if (id == R.id.activity5) {
            si = new Intent(this, AIActivity.class);
            startActivity(si);
        }
        else if (id == R.id.activity6) {
            si = new Intent(this, SensorsActivity.class);
            startActivity(si);
        }
        else {
            si = new Intent(this, GraphicsActivity.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}