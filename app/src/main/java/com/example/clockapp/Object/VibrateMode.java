package com.example.clockapp.Object;

import java.io.Serializable;

public class VibrateMode implements Serializable {
    String name;
    Integer[] pattern;
    boolean turnOn = false;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getPattern() {
        return pattern;
    }

    public void setPattern(Integer[] pattern) {
        this.pattern = pattern;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
