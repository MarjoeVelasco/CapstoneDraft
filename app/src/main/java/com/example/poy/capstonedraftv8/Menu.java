package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    Button calendar,events,weather,record_log,profile,crops;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        crops= (Button)findViewById(R.id.crops);
        calendar= (Button)findViewById(R.id.calendar);
        events= (Button)findViewById(R.id.events);
        weather= (Button)findViewById(R.id.weather);
        record_log= (Button)findViewById(R.id.record_log);
        profile= (Button)findViewById(R.id.profile);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Event_list.class);
                startActivity(intent);
                finish();
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Weather_pane.class);
                startActivity(intent);
                finish();
            }
        });

        crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,AddCrop.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
Message.messageShort(getApplicationContext(),"Press again to exit ");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
