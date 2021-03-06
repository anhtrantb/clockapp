package com.example.clockapp.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clockapp.service.AlarmService;

public class BrStopAlarmService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        context.stopService(intentService);
    }
}
