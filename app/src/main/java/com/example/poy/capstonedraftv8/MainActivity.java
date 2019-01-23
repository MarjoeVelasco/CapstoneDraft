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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ArrayList<String> myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(date_head);

        date=date_first;
        date_pass=date;

        myList = new ArrayList<String>();

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
                    String event;
                    String time_event="";
                    while (dbres2.moveToNext()) {


                        event="\t\t"+String.format(dbres2.getString(3))+" at "+String.format(dbres2.getString(5))+"\n";
                        myList.add(event);

                    }


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                            MainActivity.this);

                    LayoutInflater inflater = getLayoutInflater();

                    // create view for add item in dialog

                    View convertView = (View) inflater.inflate(R.layout.listview, null);

                    // on dialog cancel button listner

                    alertDialog.setNegativeButton("Cancel",

                            new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog,

                                                    int which) {

                                    myList.clear();



                                }

                            });



                    // add custom view in dialog

                    alertDialog.setView(convertView);

                    ListView lv = (ListView) convertView.findViewById(R.id.mylistview);

                    final AlertDialog alert = alertDialog.create();

                    alert.setTitle(" EVENTS"); // Title

                    MyAdapter myadapter = new MyAdapter(MainActivity.this,

                            R.layout.listview_item, myList);

                    lv.setAdapter(myadapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override

                        public void onItemClick(AdapterView<?> arg0, View arg1,

                                                int position, long arg3) {

                            // TODO Auto-generated method stub

                            Toast.makeText(MainActivity.this,

                                    "You have selected -: " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();

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

    class MainListHolder {

        private TextView tvText;

    }



    private class ViewHolder {

        TextView tvSname;

    }

    class MyAdapter extends ArrayAdapter<String> {

        LayoutInflater inflater;

        Context myContext;

        List<String> newList;

        public MyAdapter(Context context, int resource, List<String> list) {

            super(context, resource, list);

            // TODO Auto-generated constructor stub

            myContext = context;

            newList = list;

            inflater = LayoutInflater.from(context);

        }

        @Override

        public View getView(final int position, View view, ViewGroup parent) {

            final ViewHolder holder;

            if (view == null) {

                holder = new ViewHolder();

                view = inflater.inflate(R.layout.listview_item, null);

                holder.tvSname = (TextView) view.findViewById(R.id.tvtext_item);

                view.setTag(holder);

            } else {

                holder = (ViewHolder) view.getTag();

            }

            holder.tvSname.setText(newList.get(position).toString());



            return view;

        }

    }


}