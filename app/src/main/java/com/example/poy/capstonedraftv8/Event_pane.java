package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Event_pane extends AppCompatActivity {

    TextView tryid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pane);

        tryid = (TextView)findViewById(R.id.tryid);

        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String event_id = (String) intent.getSerializableExtra("event_id");
        String date_id = (String)intent.getSerializableExtra("date_id");

        tryid.setText(event_id+" "+date_id);

    }
}
