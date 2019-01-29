package com.example.poy.capstonedraftv8;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Event_list extends AppCompatActivity {

    ArrayList<DataModelEventList> dataModels2;
    private CustomAdapterListview mAdapter;

    SwipeController swipeController = null;
    myDbAdapter helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);


        helper = new myDbAdapter(this);


        dataModels2= new ArrayList<>();

        setPlayersDataAdapter();
        setupRecyclerView();



    }

    private void setPlayersDataAdapter() {
        Cursor dbres = helper.getAllData();
        if(dbres.getCount() == 0)
        {

            Snackbar.make(findViewById(android.R.id.content),"No Events Yet",Snackbar.LENGTH_SHORT).show();

        }
        else {
            String event;
            String time_event="";
            while (dbres.moveToNext()) {

                String event_name=String.format(dbres.getString(3));
                String event_time=String.format(dbres.getString(5));
                String event_id=String.format(dbres.getString(0));
                String date_id=String.format(dbres.getString(6));
                String color2=String.format(dbres.getString(2));
                String event_date=String.format(dbres.getString(4));
                int color=Integer.parseInt(color2);
                dataModels2.add(new DataModelEventList(event_name, event_time,event_date,event_id,date_id,color));




            }

            mAdapter= new CustomAdapterListview(dataModels2);
        }

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                DataModelEventList dataModel = dataModels2.get(position);
                String id=dataModel.getDate_id();
                helper.delete(id);
                Snackbar.make(findViewById(android.R.id.content),"Event Deleted",Snackbar.LENGTH_SHORT).show();
                mAdapter.events.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {
                Snackbar.make(findViewById(android.R.id.content),"Underconstruction d pa tapos",Snackbar.LENGTH_SHORT).show();

            }


        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Event_list.this,Menu.class);
        startActivity(intent);
        finish();
    }
}
