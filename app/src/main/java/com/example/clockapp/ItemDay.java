package com.example.clockapp;

public class ItemDay {

    String day;
    boolean isChecked = false;//mặc định 0 check
    public ItemDay(String day){
        this.day = day;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
