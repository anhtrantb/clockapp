package com.example.clockapp;

import android.icu.util.Calendar;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;

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
        return dayOfWeek;
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

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String displayDate() {
        return this.dayOfWeek + ", " + this.day + " Th" + this.month;
    }

    public static String getTimeStringFromMilliseconds(long milliseconds) {
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

}
