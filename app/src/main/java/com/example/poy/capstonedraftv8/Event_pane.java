package com.example.poy.capstonedraftv8;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;




public class Event_pane extends AppCompatActivity {

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView btn;
    Button save,del;

    String id="";
    String color="";
    String event_name="";
    String event_date="";
    String event_time="";

    String event_date_start="";
    String event_date_end="";
    int flag;

    public static String title;
    public static String start_date;
    public static String end_date;
    public static String start_time;

    public static int mPickedColor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_event_pane);

        helper = new myDbAdapter(this);
        save=(Button)findViewById(R.id.save2);
        del=(Button)findViewById(R.id.del);
        pane = (TextView) findViewById(R.id.textView);
        pane2 = (TextView) findViewById(R.id.textView7);
        time = (TextView) findViewById(R.id.time);
        r1 = (TextView) findViewById(R.id.r1);
        desc = (TextView) findViewById(R.id.desc2);

        Intent intent = getIntent();
//Get the USERNAME passed from IntentExampleActivity
        String event_id = (String) intent.getSerializableExtra("event_id");
        String date_id = (String)intent.getSerializableExtra("date_id");

        Cursor dbres = helper.getEventInfo(date_id);

        pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        pane2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragmentEnd();
                newFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a GridView object from ColorPicker class
                GridView gv = (GridView) ColorPicker.getColorPicker(Event_pane.this);



                // Initialize a new AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(Event_pane.this,R.style.MyAlertDialogTheme);

                // Set the alert dialog content to GridView (color picker)
                builder.setTitle(Html.fromHtml("<font color='#000000'>Choose the color</font>"));
                builder.setView(gv);

                // Initialize a new AlertDialog object
                final AlertDialog dialog = builder.create();

                // Show the color picker window
                dialog.show();

                // Set the color picker dialog size


                // Set an item click listener for GridView widget
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the pickedColor from AdapterView
                        mPickedColor = (int) parent.getItemAtPosition(position);

                        // Set the layout background color as picked color
                        r1.setBackgroundColor(mPickedColor);

                        // close the color picker
                        dialog.dismiss();
                    }
                });
            }
        });







        if(dbres.getCount() == 1)
        {
            flag=1;


            while (dbres.moveToNext()){

                id= String.format(dbres.getString(6));
                color = String.format(dbres.getString(2));
                event_name = String.format(dbres.getString(3));
                event_date = String.format(dbres.getString(4));
                event_time = String.format(dbres.getString(5));

            }

            pane.setText(event_date);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);
            pane2.setText(event_date);
            time.setText(event_time);
            desc.setText(event_name);


        }
        else
        {
            flag=0;

            Cursor dbres2 = helper.getStartDate(date_id);

            while (dbres2.moveToNext()) {

                id= String.format(dbres2.getString(6));
                color = String.format(dbres2.getString(2));
                event_name = String.format(dbres2.getString(3));
                event_date_start = String.format(dbres2.getString(4));
                event_time = String.format(dbres2.getString(5));


            }

            Cursor dbres3 = helper.getEndDate(date_id);

            while (dbres3.moveToNext()) {

                event_date_end = String.format(dbres3.getString(4));


            }

            pane.setText(event_date_start);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);
            pane2.setText(event_date_end);
            time.setText(event_time);
            desc.setText(event_name);




        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_date=pane.getText().toString();
                end_date=pane2.getText().toString();
                start_time=time.getText().toString();

                title=desc.getText().toString();

                if(start_date.equals(end_date))
                {

                    String f_timestamp=start_date+" "+start_time;


                    long millisSinceEpoch =epochConverter(f_timestamp);

                    int a= helper.updateEvent( millisSinceEpoch, mPickedColor,title,start_date,start_time,id);
                    if(a<=0)
                    {
                        Message.message(getApplicationContext(),"Unsuccessful");

                    } else {
                        Message.message(getApplicationContext(),"Event Updated");

                    }
                    //Message.message(getApplicationContext(),f_timestamp+" "+millisSinceEpoch);


                }
                else
                {
                    Cursor dbres4 = helper.getDateIdMax();
                    String d_id="";


                    try
                    {
                        while (dbres4.moveToNext()) {

                            d_id=String.format(dbres4.getString(0));
                            if(d_id.equals("0"))
                            {
                                d_id="0";
                            }

                        }

                        int dd_id=Integer.parseInt(d_id);
                        dd_id=dd_id+1;

                        String[] a = start_date.split("-");
                        String[] b = end_date.split("-");

                        int c=Integer.parseInt(a[2]);
                        int d=Integer.parseInt(b[2]);

                        int e=Integer.parseInt(a[1]);
                        String ee;
                        if(e<=9)
                        {
                            ee="0"+e;
                        }
                        else
                        {
                            ee=String.valueOf(e);
                        }

                        if(d>c)
                        {


                            String aa;
                            for(int i=c;i<=d;i++)
                            {
                                if(i<=9)
                                {
                                    aa=a[0]+"-"+ee+"-"+"0"+i;
                                }
                                else
                                {
                                    aa=a[0]+"-"+ee+"-"+i;
                                }
                                String bb=start_time;



                                String f_timestamp=aa+" "+bb;

                                long millisSinceEpoch =epochConverter(f_timestamp);
                                //ccc=ccc+f_timestamp+" "+millisSinceEpoch+"\n";
                                helper.insertData(millisSinceEpoch,mPickedColor,title,aa,bb,dd_id);
                            }
                            //Message.message(getApplicationContext(),ccc);

                        }
                        else
                        {

                            String aa;
                            int s=d;
                            int l=c;
                            for(int i=d;i<=c;i++)
                            {
                                if(i<=9)
                                {
                                    aa=a[0]+"-"+ee+"-"+"0"+i;
                                }
                                else
                                {
                                    aa=a[0]+"-"+ee+"-"+i;
                                }
                                String bb=start_time;

                                String f_timestamp=aa+" "+bb;

                                long millisSinceEpoch =epochConverter(f_timestamp);

                                helper.insertData(millisSinceEpoch,mPickedColor,title,aa,bb,dd_id);
                            }
                        }

                        helper.delete(id);
                        Message.message(getApplicationContext(),"Event Updated");
                    }catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                    }

                }


                Intent intent3 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent3);
                finish();



            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                helper.delete(id);
                Message.message(getApplicationContext(),"Event Deleted");

                Intent intent = new Intent(Event_pane.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    public long epochConverter(String f_timestamp){


        String input = f_timestamp.replace( " " , "T" );
        LocalDateTime ldt = LocalDateTime.parse( input ) ;


        ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
        ZonedDateTime zdt = ldt.atZone( z ) ;
        Instant instant = zdt.toInstant() ;
        long millisSinceEpoch = instant.toEpochMilli() ;

        return millisSinceEpoch;
    }

    @Override
    public void onBackPressed() {
        Intent intent3 = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent3);
        finish();

    }
}
