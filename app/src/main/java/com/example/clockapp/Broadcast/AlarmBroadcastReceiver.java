package com.example.clockapp.Broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clockapp.service.AlarmService;

import java.util.ArrayList;
import java.util.Calendar;


public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getExtras().getBoolean("multiDay",false)){
            if(isToday(intent)){
               startService(context,intent);
            }
        }else{
            startService(context,intent);
        }

    }
    public boolean isToday(Intent intent){
        Calendar calendar = Calendar.getInstance();
        int today= calendar.get(Calendar.DAY_OF_WEEK);
        Bundle bundle = intent.getExtras();
        if(bundle.getBoolean("t2",false)){
            if(today == Calendar.MONDAY) return true;
        };
        if(bundle.getBoolean("t3",false)){
            if(today == Calendar.TUESDAY) return true;
        };
        if(bundle.getBoolean("t4",false)){
            if(today == Calendar.WEDNESDAY) return true;
        };
        if(bundle.getBoolean("t5",false)){
            if(today == Calendar.THURSDAY) return true;
        };
        if(bundle.getBoolean("t6",false)){
            if(today == Calendar.FRIDAY) return true;
        };
        if(bundle.getBoolean("t7",false)){
            if(today == Calendar.SATURDAY) return true;
        };
        if(bundle.getBoolean("cn",false)){
            if(today == Calendar.SUNDAY) return true;
        };
        //không có ngày nào được thiết lập
        return  false;
    }
    public void startService(Context context,Intent intent){
            Intent intentService = new Intent(context, AlarmService.class);
            intentService.putExtras(intent.getExtras());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intentService);
            } else {
                context.startService(intentService);
            }
    }
}
