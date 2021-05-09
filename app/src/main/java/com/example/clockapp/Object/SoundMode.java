package com.example.clockapp.Object;

import android.content.Context;

import com.example.clockapp.Utils.RingTone;

import java.io.Serializable;

public class SoundMode implements Serializable {
    String soundTitle;
    String soundUri;
    boolean turnOn = true;
    public static SoundMode getDefault(Context context){
        return RingTone.getFirstSong(context);
    }
    public String getSoundTitle() {
        return soundTitle;
    }

    public void setSoundTitle(String soundTitle) {
        this.soundTitle = soundTitle;
    }

    public String getSoundUri() {
        return soundUri==null?"":soundUri;
    }

    public void setSoundUri(String soundUri) {
        this.soundUri = soundUri;
    }


    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }
}
