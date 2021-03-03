package com.example.clockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.clockapp.service.AlarmBroadcastReceiver;
import com.example.clockapp.service.AlarmService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Alarm implements Serializable {
    int id;
    Time time;//thời gian
    ArrayList<String> listDaySet;//ngày thiết lập
    PauseMode pauseMode;//chế độ dừng
    SoundMode soundMode;//chế độ âm thanh
    VibrateMode vibrateMode;//chế độ rung
    String name = "";//tên báo thức
    boolean TurnOn = true;//được bật hay không
    boolean select = false;

    public Alarm() {
        this.id = getRandom();
    }

    public void schedule(Context context) {
        //thiết lập báo thức
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("uri",soundMode.getSoundUri());
//          intent.putExtra()......
        Toast.makeText(context, "Chuông báo được đặt cho " + Time.getTimeStringFromMilliseconds(getTimeLeft()), Toast.LENGTH_SHORT).show();
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().getTimeInMillis()+ getTimeLeft(),
                alarmPendingIntent
        );
    }

    //hủy alarm
    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        context.stopService(new Intent(context, AlarmService.class));
        this.TurnOn = false;
    }
    //get set

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public ArrayList<String> getListDaySet() {
        return listDaySet;
    }

    public void setListDaySet(ArrayList<String> listDaySet) {
        this.listDaySet = listDaySet;
    }

    public PauseMode getPauseMode() {
        return pauseMode;
    }

    public void setPauseMode(PauseMode pauseMode) {
        this.pauseMode = pauseMode;
    }

    public SoundMode getSoundMode() {
        return soundMode;
    }

    public void setSoundMode(SoundMode soundMode) {
        this.soundMode = soundMode;
    }

    public VibrateMode getVibrateMode() {
        return vibrateMode;
    }

    public void setVibrateMode(VibrateMode vibrateMode) {
        this.vibrateMode = vibrateMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTurnOn() {
        return TurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        TurnOn = turnOn;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getRandom() {
        Random random = new Random(10000);
        return random.nextInt();
    }

    public long getTimeLeft() {
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();
        calendar.set(time.getYear(), time.getMonth(), time.getDay(), time.getHour(), time.getMinute());
        return calendar.getTimeInMillis() - current;
    }
}
