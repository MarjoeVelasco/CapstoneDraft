package com.example.poy.capstonedraftv8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    myDbAdapter helper;
    public static CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    String date_first = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String date_head = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(new Date());
    String date;
    String date_pass;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(date_head);

        date=date_first;
        date_pass=date;

        fab = (FloatingActionButton)findViewById(R.id.fab);
        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.getEvents(3223213);
        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);
        helper = new myDbAdapter(this);
        Cursor dbres = helper.getAllData();





        if(dbres.getCount() == 0)
        {

            Message.message(getApplicationContext(),"No Events Yet");

        }
        else
        {
            while (dbres.moveToNext()){

                String a = String.format(dbres.getString(1));
                String b = String.format(dbres.getString(2));
                String event = String.format(dbres.getString(3));



                long date_time=Long.parseLong(a);
                int color=Integer.parseInt(b);


                compactCalendar.addEvent(new Event(color, date_time, event));

            }

        }



        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String date_event = (String) intent.getSerializableExtra("date_event");


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date_pass = dateFormat.format(dateClicked);


                Context context = getApplicationContext();

                Cursor dbres2 = helper.getEventData(date_pass);
                if(dbres2.getCount() == 0)
                {

                    Snackbar.make(findViewById(android.R.id.content),"No Events on this day",Snackbar.LENGTH_SHORT).show();

                }
                else {
                    String event="EVENTS \n";
                    String time_event="";
                    while (dbres2.moveToNext()) {

                        event=event+String.format(dbres2.getString(3))+" at "+String.format(dbres2.getString(5))+"\n";

                    }
                    Message.message(getApplicationContext(),event);
                }



            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddEvent.class);
                intent.putExtra("date_event",date_pass);
                startActivity(intent);
                finish();

            }
        });
    }


}