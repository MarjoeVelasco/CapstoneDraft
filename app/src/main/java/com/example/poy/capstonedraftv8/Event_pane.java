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
        save=(Button)findViewById(R.id.save);
        cancel=(Button)findViewById(R.id.cancel);
        pane = (TextView) findViewById(R.id.textView);
        pane2 = (TextView) findViewById(R.id.textView7);
        time = (TextView) findViewById(R.id.time);
        r1 = (TextView) findViewById(R.id.r1);
        desc = (TextView) findViewById(R.id.desc);

        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String event_id = (String) intent.getSerializableExtra("event_id");
        String date_id = (String)intent.getSerializableExtra("date_id");

        Cursor dbres = helper.getStartDate(date_id);




    }
}
