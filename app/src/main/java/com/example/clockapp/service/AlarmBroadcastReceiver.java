package com.example.clockapp.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.clockapp.service.AlarmService;


public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startAlarmService(context,intent);
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}
