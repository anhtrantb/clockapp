package com.example.clockapp.Object;

import java.io.Serializable;

public class PauseMode implements Serializable {
    String timeName;
    int timeValue;
    String repeatName;
    int repeatValue;
    boolean turnOn = false;
    public String getTimeName() {
        return timeName;
    }

    public PauseMode() {
    }

    public PauseMode(String timeName, int timeValue, String repeatName, int repeatValue) {
        this.timeName = timeName;
        this.timeValue = timeValue;
        this.repeatName = repeatName;
        this.repeatValue = repeatValue;
    }

    public static PauseMode getDefault(){
        return new PauseMode("5 phút",5,"3 lần",3);
    }
    public void setTimeName(String timeName) {
        this.timeName = timeName;
    }

    public int getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(int timeValue) {
        this.timeValue = timeValue;
    }

    public String getRepeatName() {
        return repeatName;
    }

    public void setRepeatName(String repeatName) {
        this.repeatName = repeatName;
    }

    public int getRepeatValue() {
        return repeatValue;
    }

    public void setRepeatValue(int repeatValue) {
        this.repeatValue = repeatValue;
    }
    public String display(){
        return this.timeName+", "+this.repeatName;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
