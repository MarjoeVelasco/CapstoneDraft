package com.example.poy.capstonedraftv8;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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


    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(date_head);

        date=date_first;
        date_pass=date;


        dataModels= new ArrayList<>();

        fab = (FloatingActionButton)findViewById(R.id.fab);



        compactCalendar = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        show();

        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.getEvents(3223213);
        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
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
                        String event;
                        String time_event="";
                        while (dbres2.moveToNext()) {

                            String event_name=String.format(dbres2.getString(3));
                            String event_time=String.format(dbres2.getString(5));
                            String event_id=String.format(dbres2.getString(0));
                            String date_id=String.format(dbres2.getString(6));
                            String color2=String.format(dbres2.getString(2));
                            int color=Integer.parseInt(color2);
                            String temp_icon=String.format(dbres2.getString(7));
                            int icon=Integer.parseInt(temp_icon);
                            dataModels.add(new DataModel(event_name, event_time, event_id,date_id,color,icon));




                        }

                    adapter= new CustomAdapter(dataModels,getApplicationContext());

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                            MainActivity.this,R.style.MyAlertDialogTheme2);

                    alertDialog.setTitle("EVENTS");

                    LayoutInflater inflater = getLayoutInflater();

                    // create view for add item in dialog

                    View convertView = (View) inflater.inflate(R.layout.listview, null);

                    // on dialog cancel button listner

                    alertDialog.setNegativeButton("Cancel",

                            new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog,

                                                    int which) {

                                    dataModels.clear();



                                }

                            });
                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dataModels.clear();
                        }
                    });



                    // add custom view in dialog

                    alertDialog.setView(convertView);

                    ListView lv = (ListView) convertView.findViewById(R.id.mylistview);

                    final AlertDialog alert = alertDialog.create();

                    alert.setTitle("EVENTS"); // Title

                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override

                        public void onItemClick(AdapterView<?> arg0, View arg1,

                                                int position, long arg3) {

                            // TODO Auto-generated method stub

                            /*Toast.makeText(MainActivity.this,

                                    "You have selected : " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();*/

                            DataModel dataModel = dataModels.get(position);

                            Message.message(getApplicationContext(),dataModel.getEvent_id()+" "+dataModel.getDate_id());

                            Intent intent = new Intent(MainActivity.this,Event_pane.class);
                            intent.putExtra("event_id",dataModel.getEvent_id());
                            intent.putExtra("date_id",dataModel.getDate_id());
                            startActivity(intent);
                            finish();


                            alert.cancel();

                        }

                    });




                    // show dialog

                    alert.show();
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



    void show(){
        compactCalendar.showCalendarWithAnimation();
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent3);
        finish();

    }


}