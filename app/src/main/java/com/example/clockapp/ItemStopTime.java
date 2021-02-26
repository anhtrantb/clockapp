package com.example.clockapp;

public class ItemStopTime {
    int index;
    String timeRound;
    String timeAll;

    public ItemStopTime(int index, String timeRound, String timeAll) {
        this.index = index;
        this.timeRound = timeRound;
        this.timeAll = timeAll;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimeRound() {
        return timeRound;
    }

    public void setTimeRound(String timeRound) {
        this.timeRound = timeRound;
    }

    public String getTimeAll() {
        return timeAll;
    }

    public void setTimeAll(String timeAll) {
        this.timeAll = timeAll;
    }
}
