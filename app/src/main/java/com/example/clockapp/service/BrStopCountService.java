package com.example.clockapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BrStopCountService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, ServiceCountTime.class);
        context.stopService(intentService);
    }
}
