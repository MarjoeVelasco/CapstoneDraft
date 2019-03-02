package com.example.poy.capstonedraftv8;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Event_list extends Activity {

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

        String date_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Cursor dbres = helper.getEventDataList(date_today);
        if(dbres.getCount() == 0)
        {

            Snackbar.make(findViewById(android.R.id.content),"No Events Yet",Snackbar.LENGTH_SHORT).show();

        }
        else {
            String event;
            String time_event="";
            String crop="";
            String crop_name="";
            String variety="";
            while (dbres.moveToNext()) {

                String event_name=String.format(dbres.getString(3));
                String event_time=String.format(dbres.getString(5));
                String event_id=String.format(dbres.getString(0));
                String color2=String.format(dbres.getString(2));
                String event_date=String.format(dbres.getString(4));
                int color=Integer.parseInt(color2);
                String date_id=String.format(dbres.getString(6));

                String temp_icon=String.format(dbres.getString(7));
                int icon=Integer.parseInt(temp_icon);

                String temp_crop_id=String.format(dbres.getString(8));
                Cursor dbres8 = helper.getCropData(temp_crop_id);
                while (dbres8.moveToNext()) {

                    crop=String.format(dbres8.getString(2));
                    crop_name=String.format(dbres8.getString(1));
                    variety=String.format(dbres8.getString(3));

                }

                    dataModels2.add(new DataModelEventList(event_name, event_time,event_date,event_id,date_id,color,icon,crop,crop_name,variety));

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
               final String id=dataModel.getDate_id();
                final int a=position;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                        Event_list.this,R.style.MyAlertDialogTheme2);

                alertDialog.setTitle("DELETE");
                final TextView mess = new TextView(Event_list.this);
                mess.setText("\n\t\tAre you sure?");
                mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, getApplicationContext().getResources().getDisplayMetrics()));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mess.setLayoutParams(lp);
                alertDialog.setView(mess);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mAdapter.events.remove(a);
                        mAdapter.notifyItemRemoved(a);
                        mAdapter.notifyItemRangeChanged(a, mAdapter.getItemCount());

                        helper.delete(id);
                        Snackbar.make(findViewById(android.R.id.content),"Event Deleted",Snackbar.LENGTH_SHORT).show();


                    }
                });

                alertDialog.setNegativeButton("Cancel",

                        new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog,

                                                int which) {
                                dialog.cancel();



                            }

                        });





                alertDialog.show();
            }

            @Override
            public void onLeftClicked(int position) {
                DataModelEventList dataModel = dataModels2.get(position);
                final String id=dataModel.getDate_id();
                final int a=position;
                Intent intent =new Intent(Event_list.this,Event_pane2.class);
                intent.putExtra("date_id",id);
                startActivity(intent);
                finish();


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
