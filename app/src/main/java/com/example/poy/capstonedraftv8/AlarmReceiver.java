package com.example.poy.capstonedraftv8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";
    private static final String KEY_NOTIFICATION_GROUP = "com.singhajit.notificationDemo.group";

    @Override
    public void onReceive(Context context, Intent intent) {

        Drawable drawable= ContextCompat.getDrawable(context,R.drawable.care_iconv2);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        Intent notificationIntent = new Intent(context, Event_list.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Event_list.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);

        Notification notification = builder.setContentTitle("You have new Activity!")
                .setContentText(intent.getStringExtra("param"))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.harvest_iconv2)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setGroupSummary(true)
                .setGroup(KEY_NOTIFICATION_GROUP)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notification);
    }
}
