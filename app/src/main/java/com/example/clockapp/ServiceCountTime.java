package com.example.clockapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ServiceCountTime extends Service {
    final String CHANNEL_ID = "123";
    final int NOTIFICATION_ID = 1;
    NotificationManagerCompat notificationManager;
    public static final String COUNTDOWN_BR = "com.example.clockapp.countdown_br";
    Intent timeIntent = new Intent(COUNTDOWN_BR);
    int hour, minutes, seconds;
    CountDownTimer cdt = null;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        notificationManager = NotificationManagerCompat.from(this);

    }

    @Override
    public void onDestroy() {
        if(cdt!=null)
            cdt.cancel();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent1 = new Intent(this, BroadcaseStopService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,111,intent1,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Chạm để tắt báo thức!")
                .setContentText("00:00")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setOnlyAlertOnce(true);
        if(intent!=null){
        long timeSet = intent.getLongExtra("totalTime",0);
        cdt = new CountDownTimer(timeSet, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                builder.setContentText(Time.getTimeStringFromMilliseconds(millisUntilFinished));
                notificationManager.notify(NOTIFICATION_ID,builder.build());
                timeIntent.putExtra("timeMilli",millisUntilFinished);
                sendBroadcast(timeIntent);
            }

            @Override
            public void onFinish() {
                builder.setContentText("Hết giờ");
                notificationManager.notify(NOTIFICATION_ID,builder.build());
                timeIntent.putExtra("timeMilli",0);
                sendBroadcast(timeIntent);
            }
        };
        cdt.start();}
        startForeground(NOTIFICATION_ID, builder.build());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
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