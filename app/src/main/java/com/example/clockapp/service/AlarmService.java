package com.example.clockapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.example.clockapp.BroadcaseStopService;
import com.example.clockapp.R;
import com.example.clockapp.RingActivity;
import com.example.clockapp.RingTone;

import java.io.IOException;

import static android.media.RingtoneManager.TYPE_ALARM;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
final String CHANNEL_ID = "1";
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent(this, BroadcaseStopService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,111,intent1,0);
        //thông báo
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Đã đến giờ")
                .setContentText("Chạm để tắt báo thức!")
                .setSmallIcon(R.drawable.ic_speaker)
                .setContentIntent(pendingIntent)
                .build();
        //âm thanh
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("tag","start service");
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        mediaPlayer.release();
//        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //định nghĩa các thuộc tính
            String name = "name of chanel";
            String description = "describe of channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //thiết lập
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}