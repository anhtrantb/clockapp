package com.example.clockapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VibratePattern {
    final Integer[] pattern1 = {0, 100, 1000, 100, 2000};
    final Integer[] pattern2 = {0, 200, 1000, 200, 2000};
    final Integer[] pattern3 = {0, 300, 1000, 300, 2000};
    final Integer[] pattern4 = {0, 400, 1000, 400, 2000};
    final Integer[] pattern5 = {0, 500, 1000, 500, 2000};
    HashMap<String,Integer[]> list = new HashMap<>();
    public HashMap<String,Integer[]> getList(){
        HashMap<String, Integer[]> listPattern = new HashMap<>();
        listPattern.put("Basic call",pattern1);
        listPattern.put("Heartbeat",pattern2);
        listPattern.put("Ticktock",pattern3);
        listPattern.put("Waltz",pattern4);
        listPattern.put("Zig-zig-zig",pattern5);
        return listPattern;
    }
    public String getFirst(){
        return "Basic call";
    }
    public VibrateMode getDefaut(){
        VibrateMode vibrateMode = new VibrateMode();
        vibrateMode.setName("Basic call");
        vibrateMode.setPattern(pattern1);
        return  vibrateMode;
    }
}
