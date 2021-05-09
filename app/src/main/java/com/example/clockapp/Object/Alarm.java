package com.example.clockapp.Object;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.clockapp.Broadcast.AlarmBroadcastReceiver;
import com.example.clockapp.service.AlarmService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Alarm implements Serializable {
    int id = 0;
    Time time;//thời gian
    PauseMode pauseMode;//chế độ dừng
    SoundMode soundMode;//chế độ âm thanh
    //thời gian hàng ngày
    boolean Monday  = false;
    boolean Tuesday = false;
    boolean Wednesday = false;
    boolean Thursday = false;
    boolean Friday = false;
    boolean Saturday = false;
    boolean Sunday = false;
    //---------------------------
    boolean vibrate = true;
    String name = "";//tên báo thức
    boolean TurnOn = true;//được bật hay không
    boolean select = false;
    boolean quiz = false;

    public boolean isQuiz() {
        return quiz;
    }

    public void setQuiz(boolean quiz) {
        this.quiz = quiz;
    }

    public Alarm() {
        this.id = getRandom();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    //get set

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
        //tự động sinh ra id
        return new Random().nextInt(Integer.MAX_VALUE)+10;
    }

    public long getTimeLeft() {
        Calendar calendar = Calendar.getInstance();
        long current = calendar.getTimeInMillis();
        calendar.set(time.getYear(), time.getMonth(), time.getDay(), time.getHour(), time.getMinute());
        return calendar.getTimeInMillis() - current;
    }

    public boolean isMonday() {
        return Monday;
    }

    public void setMonday(boolean monday) {
        Monday = monday;
    }

    public boolean isTuesday() {
        return Tuesday;
    }

    public void setTuesday(boolean tuesday) {
        Tuesday = tuesday;
    }

    public boolean isWednesday() {
        return Wednesday;
    }

    public void setWednesday(boolean wednesday) {
        Wednesday = wednesday;
    }

    public boolean isThursday() {
        return Thursday;
    }

    public void setThursday(boolean thursday) {
        Thursday = thursday;
    }

    public boolean isFriday() {
        return Friday;
    }

    public void setFriday(boolean friday) {
        Friday = friday;
    }

    public boolean isSaturday() {
        return Saturday;
    }

    public void setSaturday(boolean saturday) {
        Saturday = saturday;
    }

    public boolean isSunday() {
        return Sunday;
    }

    public void setSunday(boolean sunday) {
        Sunday = sunday;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}
