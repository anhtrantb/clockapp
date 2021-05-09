package com.example.clockapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.clockapp.Activity.LockScreenActivity;
import com.example.clockapp.Broadcast.BrStopAlarmService;
import com.example.clockapp.R;
import com.example.clockapp.Utils.RingTone;

import java.io.IOException;

public class AlarmService extends Service {
    final String CHANNEL_ID = "CHANNEL_FOR_ALARM";
    int repeatCount ;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Vibrator v;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
            String uri = bundle.getString("uri");
            if(uri==null||uri.isEmpty()){
                uri = RingTone.getFirstSong(this).getSoundUri();
            }
            this.repeatCount = intent.getIntExtra("repeatCount",0);
            //phats nhacj
            Uri myUri  = Uri.parse(uri);
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            try {
                mediaPlayer.setDataSource(getApplicationContext(), myUri);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //rung
            v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 200, 1000};
            if(intent.getExtras().getBoolean("vibrate")){
                v.vibrate(pattern,0);
            }
        //build notification
        Intent fullScreenIntent = new Intent(this, LockScreenActivity.class);
        fullScreenIntent.putExtra("quiz",bundle.getBoolean("quiz",false));
        fullScreenIntent.putExtra("id",bundle.getInt("id",1234));
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        createNotificationChannel();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_clock)
                        .setContentTitle("Báo thức")
                        .setContentText("Chạm để tắt báo thức........")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setFullScreenIntent(fullScreenPendingIntent, true);
        notificationBuilder.setAutoCancel(true);
        Notification notification = notificationBuilder.build();
        startForeground(1, notification);
        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        v.cancel();
        stopForeground(true);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //định nghĩa các thuộc tính
            String name = "alarm";
            String description = "channel for show alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            //thiết lập
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
