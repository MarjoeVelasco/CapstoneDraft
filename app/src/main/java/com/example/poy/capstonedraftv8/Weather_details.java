package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;



public class Weather_details extends AppCompatActivity {

    TextView show;


    public static ArrayList list1 = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        show = (TextView)findViewById(R.id.show);


        Intent intent = getIntent();
        String date_id = (String)intent.getSerializableExtra("weather_date");



        int start=list1.indexOf(date_id);
        String str="";

        for(int i=start;i<=start+10;i++)
        {
            str=str+list1.get(i)+"\n";
        }

        show.setText(str);



    }
}
