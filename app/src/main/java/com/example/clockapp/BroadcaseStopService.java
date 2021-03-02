package com.example.clockapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clockapp.service.AlarmService;

public class BroadcaseStopService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ServiceCountTime.class);
        context.stopService(intentService);
    }
}
