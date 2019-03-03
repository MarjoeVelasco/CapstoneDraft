package com.example.poy.capstonedraftv8;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;


public class BootReceiver extends BroadcastReceiver

{
    myDbAdapter helper;


    public void onReceive(Context context, Intent intent)
    {
        //  execute when Boot Completed
        helper = new myDbAdapter(context);


        Calendar cal = Calendar.getInstance();
        long dateChecker = cal.getTimeInMillis();

        try {

            int id = 0;
            long date_time = 0L;
            String title = "";

            String crop_name = "";
            String crop = "";

            Cursor dbres22 = helper.getAllData();
            if (dbres22.getCount() != 0) {

                while (dbres22.moveToNext()) {


                    String tempDate = String.format(dbres22.getString(1));
                    date_time = Long.parseLong(tempDate);

                    if (dateChecker < date_time) {
                        String id2 = String.format(dbres22.getString(0));
                        String title2 = String.format(dbres22.getString(3));
                        String crop_id = String.format(dbres22.getString(8));

                        Cursor dbres2 = helper.getCropData(crop_id);

                        if(dbres2.getCount()!=0)
                        {

                            while (dbres2.moveToNext()) {

                                crop_name = String.format(dbres2.getString(1));
                                crop=String.format(dbres2.getString(2));
                                String temp_title=title2+" of "+crop+"("+crop_name+")";
                                title=title+id2+" "+date_time+" "+temp_title+"\n";


                                //NOTIF*************

                                AddEvent.alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
                                Intent notificationIntent = new Intent(context, AlarmReceiver.class);

                                notificationIntent.putExtra("param", temp_title);
                                AddEvent.broadcast = PendingIntent.getBroadcast(context,Integer.parseInt(id2), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
                                AddEvent.alarmManager.setExact(AlarmManager.RTC_WAKEUP, date_time, AddEvent.broadcast);

                                //END OF NOTIF*************

                            }
                        }

                    }


                }
                Message.message(context,title);


            }
        }
        catch (Exception e)
        {
            Message.message(context,e.getMessage());
        }








        Toast.makeText(context, "Booting Completed", Toast.LENGTH_LONG).show();
    }

}