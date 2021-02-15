package com.example.clockapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Alarm implements Serializable {
     Time time;//thời gian
     String name;

     String soundTitle;
     String soundUri;
     boolean hasSound;

     int vibrateMode;
     boolean hasVibrate;

     int pauseTime;
     int repeat;
     boolean hasPause;

     boolean hasReadLoudTime;
     ArrayList<String> listDaySet;
     boolean isSet;//được bật hay không

     public Time getTime() {
          return time;
     }

     public void setTime(Time time) {
          this.time = time;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getSoundTitle() {
          return soundTitle;
     }

     public void setSoundTitle(String soundTitle) {
          this.soundTitle = soundTitle;
     }

     public String getSoundUri() {
          return soundUri;
     }

     public void setSoundUri(String soundUri) {
          this.soundUri = soundUri;
     }

     public int getVibrateMode() {
          return vibrateMode;
     }

     public void setVibrateMode(int vibrateMode) {
          this.vibrateMode = vibrateMode;
     }

     public int getPauseTime() {
          return pauseTime;
     }

     public void setPauseTime(int pauseTime) {
          this.pauseTime = pauseTime;
     }

     public int getRepeat() {
          return repeat;
     }

     public void setRepeat(int repeat) {
          this.repeat = repeat;
     }

     public boolean isHasSound() {
          return hasSound;
     }

     public void setHasSound(boolean hasSound) {
          this.hasSound = hasSound;
     }

     public boolean isHasReadLoudTime() {
          return hasReadLoudTime;
     }

     public void setHasReadLoudTime(boolean hasReadLoudTime) {
          this.hasReadLoudTime = hasReadLoudTime;
     }

     public ArrayList<String> getListDaySet() {
          return listDaySet;
     }

     public void setListDaySet(ArrayList<String> listDaySet) {
          this.listDaySet = listDaySet;
     }

     public boolean isSet() {
          return isSet;
     }

     public void setSet(boolean set) {
          isSet = set;
     }

     public boolean isHasVibrate() {
          return hasVibrate;
     }

     public void setHasVibrate(boolean hasVibrate) {
          this.hasVibrate = hasVibrate;
     }

     public boolean isHasPause() {
          return hasPause;
     }

     public void setHasPause(boolean hasPause) {
          this.hasPause = hasPause;
     }
}
