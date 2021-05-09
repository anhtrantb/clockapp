package com.example.clockapp.Object;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.clockapp.Broadcast.AlarmBroadcastReceiver;

import java.util.Calendar;

public class Util {

    public static void scheduleAlarm(Context context, Alarm alarm) {
        boolean multiDay = false;
        if(alarm.isMonday()||alarm.isTuesday()||alarm.isWednesday()||alarm.isThursday()||alarm.isFriday()||alarm.isSaturday()||alarm.isSunday()){
            multiDay = true;
        }

        //thiết lập báo thức
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        //gửi đi thông tin của uri và có đặt quiz hay không
        Bundle bundle = new Bundle();
        bundle.putString("uri",alarm.getSoundMode().getSoundUri());
        bundle.putBoolean("quiz",alarm.isQuiz());
        bundle.putBoolean("t2",alarm.isMonday());
        bundle.putBoolean("t3",alarm.isTuesday());
        bundle.putBoolean("t4",alarm.isWednesday());
        bundle.putBoolean("t5",alarm.isThursday());
        bundle.putBoolean("t6",alarm.isFriday());
        bundle.putBoolean("t7",alarm.isSaturday());
        bundle.putBoolean("cn",alarm.isSunday());
        bundle.putBoolean("multiDay",multiDay);
        bundle.putBoolean("vibrate",alarm.isVibrate());
        bundle.putInt("id",alarm.getId());
        intent.putExtras(bundle);
        //---------------
        Toast.makeText(context, "Chuông báo được đặt cho " + Time.getTimeStringFromMilliseconds(alarm.getTimeLeft()), Toast.LENGTH_SHORT).show();
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context,alarm.getId() , intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(alarm.getTime().getYear(),alarm.getTime().getMonth(),alarm.getTime().getDay(),alarm.getTime().getHour(),alarm.getTime().getMinute());

        if(multiDay){
            //chọn ít nhất một ngày trong tuần
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmPendingIntent);
        }else{
            //không chọn ngày nào hết
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    alarm.getPauseMode().timeValue*1000 * 60, alarmPendingIntent);
        }

    }
    //hủy alarm
    public static void cancelAlarm(Context context, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
    }
}
