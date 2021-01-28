package com.example.clockapp;

public class ItemAlarm {
    String time;
    String date;
    boolean check;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ItemAlarm(String time, String date, boolean check) {
        this.time = time;
        this.date = date;
        this.check = check;
    }
}