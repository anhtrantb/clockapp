package com.example.clockapp;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;

import java.util.HashMap;
import java.util.Map;

public class RingTone {
    public static Map<String, String> getRingTone(Context context) {
        RingtoneManager manager = new RingtoneManager(context);
        //lấy toàn bộ ringtone trong máy
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        //con trỏ duyệt
        Cursor cursor = manager.getCursor();
        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" +
                    cursor.getString(RingtoneManager.ID_COLUMN_INDEX);
            list.put(notificationTitle, notificationUri);
        }
        return list;
    }
    public static String getFirst(Context context){
        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        cursor.moveToFirst();
        return cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
    }
    public static SoundMode getFirstSong(Context context){
        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();
        cursor.moveToFirst();
        SoundMode soundMode = new SoundMode();
        soundMode.setSoundTitle(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
        soundMode.setSoundUri(cursor.getString(RingtoneManager.ID_COLUMN_INDEX));
        return  soundMode;
    }
}
