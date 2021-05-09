package com.example.clockapp.Object;

import android.os.Build;
import android.util.TimeUtils;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Time implements Serializable {
    private String name;
    private int hour;
    private int minute;
    private int second;

    private int day;
    private int month;
    private int year;

    public Time() {
    }

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Time(String name, int hour, int minute, int second) {
        this.name = name;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    private String dayOfWeek;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDayOfWeek() {
        return dayOfWeek==null?"":dayOfWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullTime() {
        return String.format("%02d", hour) + ":" +
                String.format("%02d", minute) + ":" + String.format("%02d", second);
    }
    public String getTimeHourMinutes(){
        return String.format("%02d", hour) + ":" +
                String.format("%02d", minute);
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String displayDate() {
        return this.dayOfWeek + ", " + this.day + " Th" + (this.month+1);
    }

    public static String getTimeStringFromMilliseconds(long milliseconds) {
//        long hour = TimeUnit.MILLISECONDS.toHours(milliseconds);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        //trả về định dạng hh:mm:ss nếu giờ >0
        // trả về mm:ss nếu giờ = 0;
        int seconds = (int) (milliseconds / 1000);//tổng số giây
        int hour = seconds / 3600;
        int minutes = seconds /
                60 % 60;
        seconds = seconds % 60;
        if (hour == 0) {
            return (
                    String.format("%02d", minutes) + " : "
                            + String.format("%02d", seconds));
        } else {
            return ((String.format("%02d", hour) + " : " +
                    String.format("%02d", minutes) + " : "
                    + String.format("%02d", seconds)));
        }
    }
    public static Time getCurrentTime(){
        Time timeCurrent = new Time();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        timeCurrent.setDay(calendar.get(java.util.Calendar.DATE));
        timeCurrent.setMonth(calendar.get(java.util.Calendar.MONTH));
        timeCurrent.setYear(calendar.get(Calendar.YEAR));
        return  timeCurrent;
    }

}
