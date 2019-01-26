package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Event_pane extends AppCompatActivity {

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView btn;
    Button save,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pane);

        helper = new myDbAdapter(this);
        save=(Button)findViewById(R.id.save2);
        cancel=(Button)findViewById(R.id.cancel2);
        pane = (TextView) findViewById(R.id.textView13);
        pane2 = (TextView) findViewById(R.id.textView19);
        time = (TextView) findViewById(R.id.time2);
        r1 = (TextView) findViewById(R.id.r1);
        desc = (TextView) findViewById(R.id.desc2);

        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String event_id = (String) intent.getSerializableExtra("event_id");
        String date_id = (String)intent.getSerializableExtra("date_id");

        Cursor dbres = helper.getEventInfo(date_id);
        Cursor dbres2 = helper.getStartDate(date_id);


        if(dbres.getCount() == 1)
        {
            String id="";
            String color="";
            String event_name="";
            String event_date="";
            String event_time="";

            while (dbres.moveToNext()){

                id= String.format(dbres.getString(0));
                color = String.format(dbres.getString(2));
                event_name = String.format(dbres.getString(3));
                event_date = String.format(dbres.getString(4));
                event_time = String.format(dbres.getString(5));

            }

            pane.setText(event_date);
            r1.setBackgroundColor(Integer.parseInt(color));
            pane2.setText(event_date);
            time.setText(event_time);
            desc.setText(event_name);

        }
        else
        {
            /*String id="";
            String color="";
            String event_name="";
            String event_date_start="";
            String event_date_end="";
            String event_time="";



            while (dbres2.moveToNext()) {

                id= String.format(dbres.getString(0));
                color = String.format(dbres.getString(2));
                event_name = String.format(dbres.getString(3));
                event_date_start = String.format(dbres.getString(4));
                event_time = String.format(dbres.getString(5));

            }

            Cursor dbres3 = helper.getEndDate(date_id);

            while (dbres3.moveToNext()) {

                event_date_end = String.format(dbres.getString(4));

            }

            pane.setText(event_date_start);
            r1.setBackgroundColor(Integer.parseInt(color));
            pane2.setText(event_date_end);
            time.setText(event_time);
            desc.setText(event_name);

            */


        }


    }
}
