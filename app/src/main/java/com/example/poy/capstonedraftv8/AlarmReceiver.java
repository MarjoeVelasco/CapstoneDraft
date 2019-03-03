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
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.app.NotificationManager.IMPORTANCE_LOW;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";
    private static final String KEY_NOTIFICATION_GROUP = "com.singhajit.notificationDemo.group";

    int SUMMARY_ID = 0;
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    @Override
    public void onReceive(Context context, Intent intent) {

        Drawable drawable= ContextCompat.getDrawable(context,R.drawable.care_iconv2);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        Intent notificationIntent = new Intent(context, Event_list.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Event_list.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, 0);

       // NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);



       /* NotificationCompat.Builder groupBuilder = builder.setContentTitle("You have new Activity!")
                        .setContentText("wassup")
                        .setGroupSummary(true)
                        .setGroup("GROUP_1")
                        .setGroup(KEY_NOTIFICATION_GROUP)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("wassup"))
                        .setContentIntent(pendingIntent);

        Notification notification = builder.setContentTitle("You have new Activity!")
                .setContentText(intent.getStringExtra("param"))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.harvest_iconv2)
                .setLargeIcon(bitmap)
                .setAutoCancel(true)
                .setGroup(KEY_NOTIFICATION_GROUP)
                .setContentIntent(pendingIntent).build();*/

        Notification newMessageNotification1 = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.harvest_iconv2)
                        .setContentTitle("New Activity Alert!")
                        .setContentText(intent.getStringExtra("param"))
                        .setLargeIcon(bitmap)
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                         .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                         .setContentIntent(pendingIntent)
                        .build();


        Notification summaryNotification  = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setContentTitle("You have new activities")
                        .setContentText("Two new messages")
                        .setSmallIcon(R.drawable.harvest_iconv2)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine(" ")
                                .addLine(" ")
                                .setBigContentTitle(" ")
                                .setSummaryText("New Activities"))
                        //specify which group this notification belongs to
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                      .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .setSound(null)
                        .build();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );

        }*/
        /*NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        manager.notify(1, groupBuilder.build());
        manager.notify(m, notification);*/

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, newMessageNotification1);
        notificationManager.notify(SUMMARY_ID, summaryNotification);
    }
}
